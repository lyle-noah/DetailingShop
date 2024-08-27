function showContent(contentId) {
    var i;
    var contents = document.getElementsByClassName("content");
    for (i = 0; i < contents.length; i++) {
        contents[i].style.display = "none";
    }
    document.getElementById(contentId).style.display = "block";
}
