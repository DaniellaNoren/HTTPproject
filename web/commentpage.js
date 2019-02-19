/*
var comments = [
    {
        "message": "Message generated from javascript! connect this functionality with database somehow..."
    },
    {
        "message": "another message from javascript"
    }
];


var messagesContainer = document.getElementById("messages-container");

comments.forEach(function(comment){
    var messageDiv = document.createElement("div");
    messageDiv.setAttribute("class", "message");
    messageDiv.innerHTML = comment.message;
    messagesContainer.appendChild(messageDiv);
});
*/




getPosts(function(posts){
    renderPosts(posts);
});


function getPosts(callback){
    var request = new XMLHttpRequest();

    request.onload = function(){
        var posts = JSON.parse(request.responseText);
        callback(posts);
    }
    request.open("GET", "testmessages.json", true);
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










