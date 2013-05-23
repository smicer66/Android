package com.scanshop;

public class RowData
{
  protected String mDetail;
  protected int mId;
  protected String mTitle;

  RowData(int paramInt, String paramString1, String paramString2)
  {
    this.mId = paramInt;
    this.mTitle = paramString1;
    this.mDetail = paramString2;
  }

  public String toString()
  {
    return this.mId + " " + this.mTitle + " " + this.mDetail;
  }
}
