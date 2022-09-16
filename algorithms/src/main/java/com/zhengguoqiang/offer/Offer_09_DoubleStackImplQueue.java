package com.zhengguoqiang.offer;

import java.util.LinkedList;

/**
 * 剑指 Offer 09. 用两个栈实现队列
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数 appendTail 和 deleteHead ，分别完成在队列尾部插入整数和在队列头部删除整数的功能。(若队列中没有元素，deleteHead 操作返回 -1 )
 *
 *
 * 示例 1：
 *
 * 输入：
 * ["CQueue","appendTail","deleteHead","deleteHead"]
 * [[],[3],[],[]]
 * 输出：[null,null,3,-1]
 * 示例 2：
 *
 * 输入：
 * ["CQueue","deleteHead","appendTail","appendTail","deleteHead","deleteHead"]
 * [[],[],[5],[2],[],[]]
 * 输出：[null,-1,null,null,5,2]
 * 提示：
 *
 * 思路：可以设计栈 A 用于加入队尾操作，栈 B 用于将元素倒序，从而实现删除队首元素
 */
public class Offer_09_DoubleStackImplQueue {
    //A.新加入的元素
    //B.倒序存放A中的元素
    LinkedList<Integer> A,B;

    public Offer_09_DoubleStackImplQueue() {
        A = new LinkedList<>();
        B = new LinkedList<>();
    }

    public void appendTail(int value){
        A.addLast(value);
    }

    public int deleteHead(){
        //B不为空，直接弹出栈顶元素
        if (!B.isEmpty()) return B.removeLast();
        //B为空，A不为空，将A中的元素依次弹出并放入B中
        if (!A.isEmpty()){
            while (!A.isEmpty()){
                B.addLast(A.removeLast());
            }
            return B.removeLast();
        }
        //A,B都为空，返回-1
        return -1;
    }
}
