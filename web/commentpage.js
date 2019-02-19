getMessages(function(posts){
    renderPosts(posts);
});


function getMessages(callback){
    var request = new XMLHttpRequest();

    request.onload = function(){
        var posts = JSON.parse(request.responseText);
        callback(posts);
    }
    request.open("GET", "comments.json", true);
    request.send(null);
}


function renderPosts(posts) {
    var messagesContainer = document.getElementById("messages-container");

    posts.forEach(function(post){
        var message = document.createElement("div");

        message.innerHTML = post.name;
        message.setAttribute("class", "message");

        messagesContainer.appendChild(message);
    });
}










