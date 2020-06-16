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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.BlogComment;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 

// Servlet to load and list all previous comments
@WebServlet("/load-comments")
public final class LoadCommentsServlet extends HttpServlet {

  private Gson gson = new Gson(); 
  private static final String JSON_CONTENT_TYPE = "application/json";

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get comments sorting order 
    String sortingOrder = request.getParameter("sort");

    if(sortingOrder == null) {
        sortingOrder = "SortDirection.DESCENDING";
    }

    // Get BlogComment entities from Datastore
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Query query = new Query("BlogComment").addSort("postDate", sortingOrder);
    PreparedQuery results = datastore.prepare(query);

    ArrayList<BlogComment> commentsList = new ArrayList<BlogComment>();

    for (Entity entity : results.asIterable()) {

      Date postDate = (Date) entity.getProperty("postDate");
      String text = (String) entity.getProperty("text");

      BlogComment c = new BlogComment(postDate, text);
      commentsList.add(c);
    }
    
    // Convert the comments data to JSON
    String json = convertToJsonUsingGson(commentsList);

    // Send the JSON as the response
    response.setContentType(JSON_CONTENT_TYPE);
    response.getWriter().println(json);

  }

  /**
   * Converts a BlogComments instance into a JSON string using the Gson library. Note: We first added
   * the Gson library dependency to pom.xml.
   */
  private String convertToJsonUsingGson(ArrayList<BlogComment> comments) {
    
    String json = gson.toJson(comments);
    
    return json;
  }
}
