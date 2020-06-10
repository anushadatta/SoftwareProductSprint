package com.google.sps.data;

import java.util.Date;

/** Class containing blog comments. */
public final class BlogComments {

  private final Date postDate;
  private final String comment; 

  public BlogComments(Date postDate, String comment) {
    this.postDate = postDate;
    this.comment = comment;
  }

  public Date getPostDate() {
    return postDate;
  }

  public String getComment() {
    return comment;
  }

}
