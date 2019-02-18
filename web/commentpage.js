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




