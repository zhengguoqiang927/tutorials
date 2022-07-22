package com.zhengguoqiang.offer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 剑指 Offer 06. 从尾到头打印链表
 * 输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 * <p>
 * 示例 1：
 * <p>
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 * <p>
 * 限制：
 * <p>
 * 0 <= 链表长度 <= 10000
 */
public class Offer_06_ReversePrint {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            this.val = x;
        }
    }

    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * @param head
     * @return
     */
    public int[] reversePrint_S1(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int[] arr = new int[list.size()];
        for (int i = list.size() - 1; i >= 0; i--) {
            arr[list.size() - 1 - i] = list.get(i);
        }
        return arr;
    }

    /**
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head
     * @return
     */
    public int[] reversePrint_S2(ListNode head) {
        //1.先计数
        int length = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            length++;
        }

        //2.数组从后往前存链表结点值
        int[] arr = new int[length];
        while (head != null) {
            arr[--length] = head.val;
            head = head.next;
        }
        return arr;
    }

    /**
     * 借助栈的先进后出特性
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * @param head
     * @return
     */
    public int[] reversePrint_S3(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        int size = stack.size();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = stack.pop().val;
        }
        return arr;
    }

    /**
     * 逆序打印链表-递归方式
     * @param head
     */
    public void reversePrint(ListNode head) {
        if (head == null)
            return;
        reversePrint(head.next);
        System.out.println(head.val);
    }


    public static void main(String[] args) {

    }
}
