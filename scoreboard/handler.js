'use strict';

const AWS = require('aws-sdk');
const dynamoDb = new AWS.DynamoDB.DocumentClient();
const TableName = process.env.DYNAMODB_TABLE;

module.exports.list = (event, context, callback) => {
  let items = [];
  let params = { TableName };
  const fullscan = () => {
    dynamoDb.scan(params, (error, result) => {
      if (error) {
        console.error(error);
        callback(null, { statusCode: error.statusCode || 501 });
        return;
      }
      console.log(result);
      items = items.concat(result.Items);
      if (result.LastEvaluatedKey) {
        params.ExclusiveStartKey = result.LastEvaluatedKey;
        fullscan();
      } else {
        callback(null, {
          statusCode: 200,
          headers: { "Access-Control-Allow-Origin": "*" },
          body: JSON.stringify(items),
        });
      }
    });
  };
  fullscan();
};

module.exports.update = (event, context, callback) => {
  const data = JSON.parse(event.body);
  const id = data.id;
  const score = Number(data.score);
  const submittime = new Date().toLocaleString('ja-JP');
  const Item = { id, score, submittime, };

  if (typeof id !== 'string' || typeof score !== 'number') {
    callback(null, { statusCode: 400 });
    return;
  }

  dynamoDb.get({
    TableName,
    Key: { id },
  }, (error, result) => {
    if (error) {
      console.error(error);
      callback(null, { statusCode: error.statusCode || 501 });
      return;
    }
    if (result.Item) {
      if (result.Item.score > score) {
        dynamoDb.update({
          TableName,
          Key: { id },
          ExpressionAttributeValues: {
            ':score': score,
            ':submittime': submittime,
          },
          UpdateExpression: 'SET score = :score, submittime = :submittime',
          ReturnValues: 'ALL_NEW',
        }, (error, result) => {
          if (error) {
            console.error(error);
            callback(null, { statusCode: error.statusCode || 501 });
            return;
          }
          callback(null, {
            statusCode: 200,
            body: JSON.stringify(result.Attributes),
          });
        });
      } else {
        callback(null, {
          statusCode: 200,
          body: JSON.stringify(result.Item),
        });
      }
    } else {
      dynamoDb.put({
        TableName,
        Item,
      }, (error) => {
        if (error) {
          console.error(error);
          callback(null, { statusCode: error.statusCode || 501 });
          return;
        }
        callback(null, { statusCode: 200 });
      });
    }
  });
};