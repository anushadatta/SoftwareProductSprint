package com.google.sps.data;

import java.util.Date;

/** Class defining one comment. */
public final class BlogComment {

  private final Date postDate;
  private final String text; 

  public BlogComment(Date postDate, String text) {
    this.postDate = postDate;
    this.text = text;
  }

  public Date getPostDate() {
    return postDate;
  }

  public String getCommentText() {
    return text;
  }

}
