/**
 * This class reads all the comments from the json file "comments.json" and loads them into the html file "message.html"
 * to display them in the web page.
 *
 * Every comment are made into a div and then all those divs are placed in a "div container" in the html and that's how
 * it can load any amount of them since this class creates the needed html.
 *
 * There is probably a better way of doing this than writing to and reading from a file but this is what I could
 * figure out for now.
 */

getMessages(function(posts){
    renderPosts(posts);
});


function getMessages(callback){
    var request = new XMLHttpRequest();

    request.onload = function(){
        var posts = JSON.parse(request.responseText);
        callback(posts);
    }
    request.open("GET", "../json/comments.json", true);
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










