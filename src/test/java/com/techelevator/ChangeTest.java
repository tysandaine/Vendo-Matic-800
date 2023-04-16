package com.techelevator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ChangeTest {

  @Test
    public void change_test(){
      Change getChange = new Change();
      double money = 23.65;
      int[] result = getChange.getAmount(money);
      Assert.assertEquals(94, result[0]);
      Assert.assertEquals(1, result[1]);
      Assert.assertEquals(1, result[2]);
  }

  @Test
  public void change_negative_test(){
    Change getChange = new Change();
    double money = -23.65;
    int[] result = getChange.getAmount(money);
    Assert.assertEquals(0, result[0]);
    Assert.assertEquals(0, result[1]);
    Assert.assertEquals(0, result[2]);
  }

  @Test
  public void change_zero_test(){
    Change getChange = new Change();
    double money = 0;
    int[] result = getChange.getAmount(money);
    Assert.assertEquals(0, result[0]);
    Assert.assertEquals(0, result[1]);
    Assert.assertEquals(0, result[2]);
  }
}
