// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

// Fetches Blobstore URL for form action to faciliate upload of images 
function getBlobstoreURL() {
    fetch('/blobstore-upload-url')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const commentForm = document.getElementById('comment-form');
        commentForm.action = imageUploadUrl;
      });
}

/**
 * Fetches comments from the servers and adds them to the DOM.
 */
function getBlogComments() {

    const commentsDiv = document.getElementById("comments");

    fetch('/load-comments')
        .then(response => response.json())
        .then(commentsList => {

            console.log(commentsList);

            if(commentsList.length == 0) {
                
                var noComments = document.createElement("p");
                noComments.innerText = "Be the first to comment!";

                commentsDiv.appendChild(noComments);
            } else {

                for (let i = 0; i < commentsList.length; i++) {
                    commentsDiv.appendChild(createCommentElement(commentsList[i]));
                }
            }    
        })
        .catch(error => console.log(error));
}

function createCommentElement(input) {

    console.log(input.text + input.postDate);
    
    // To prevent HTML and Script injections 
    var commentText = input.text;
    var commentText = commentText.replace(/</g, "&lt;").replace(/>/g, "&gt;");

    if (input.imageURL != null){   
        var commentHTML = `
        <div class="text">${commentText}</div>
        <div class="date">${input.postDate}</div>
        <img src="${input.imageURL}"></img>
        `;
    } else {
        var commentHTML = `
        <div class="text">${commentText}</div>
        <div class="date">${input.postDate}</div>
        `;
    }

    var comment = document.createElement("div");
    comment.classList.add("comment-card");
    comment.innerHTML = commentHTML;

    return comment;
}

function initBlog() {
    getBlobstoreURL();
    getBlogComments();
}
