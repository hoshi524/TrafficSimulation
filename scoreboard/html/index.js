$(() => {
    $.ajax({
        url: "https://atijm8gwti.execute-api.ap-northeast-1.amazonaws.com/dev/list",
        success: (result) => {
            result.sort((a, b) => {
                return a.score - b.score;
            });
            result.forEach((x, i) => {
                $("#list").append($("<tr>").append(
                    $("<td>").text(i + 1),
                    $("<td>").text(x.id),
                    $("<td>").text(x.score),
                    $("<td>").text(x.submittime),
                ));
            });
        }
    });
});