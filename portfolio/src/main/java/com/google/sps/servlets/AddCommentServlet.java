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

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

// Servlet responsible for creating new tasks.
@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get data from form
    Date postDate = new Date();
    String text = request.getParameter("comment-text");

    // Create comment entity 
    Entity commentEntity = new Entity("BlogComment");
    commentEntity.setProperty("text", text);
    commentEntity.setProperty("postDate", postDate);

    // Upload comment to Datastore 
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);

    response.sendRedirect("/blog.html");
  }
}
