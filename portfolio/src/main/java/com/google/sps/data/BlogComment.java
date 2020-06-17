package com.google.sps.data;

import java.util.Date;

/** Class defining one comment. */
public final class BlogComment {

  private final Date postDate;
  private final String text; 
  private final String imageURL;

  public BlogComment(Date postDate, String text, String imageURL) {
    this.postDate = postDate;
    this.text = text;
    this.imageURL = imageURL;
  }

  public Date getPostDate() {
    return postDate;
  }

  public String getCommentText() {
    return text;
  }

  public String getImageURL() {
    return imageURL;
  }

}
