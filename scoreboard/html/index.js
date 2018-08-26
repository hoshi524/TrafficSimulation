$(() => {
    $.ajax({
        url: "https://atijm8gwti.execute-api.ap-northeast-1.amazonaws.com/dev/list",
        success: (result) => {
            result.sort((a, b) => {
                return a.score - b.score;
            });
            result.forEach((x, i) => {
                $("#list").append("<tr><td>" + (i + 1) + "</td><td>" + x.id + "</td><td>" + x.score + "</td><td>" + x.submittime + "</td></tr>");
            });
        }
    });
});