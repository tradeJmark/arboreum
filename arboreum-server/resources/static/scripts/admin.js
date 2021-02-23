$("#newCatButton").click(event => {
    $("#addCategoryDialog")[0].showModal();
});
const form = $("#addCategoryForm")
form.submit(event => {
    event.preventDefault();
    const formObject = {}
    form.serializeArray().forEach(kv => {
        formObject[kv.name] = kv.value
    });
    console.log(formObject)
    $.ajax({
        contentType: "application/json",
        type: "POST",
        url: form.attr('action'),
        data: JSON.stringify(formObject),
        processData: false
    }).done(response => {
        $("#addCategoryDialog").hide();
        window.location.reload(true);
    })
});