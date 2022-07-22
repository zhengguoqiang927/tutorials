package com.zhengguoqiang.offer;

import com.zhengguoqiang.offer.Offer_06_ReversePrint.ListNode;

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
 *
 * 解题思路：
 * 1.统计元素个数，数组从后向前存放元素
 * 2.借助栈
 * 3.链表逆序打印
 * 4.反转链表，顺序打印
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
     * 思路：先遍历链表统计元素的数量，然后申请数组空间，在遍历链表然后从后往前 向数组中存放元素
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head
     * @return
     */
    public int[] reversePrint_S1(ListNode head) {
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
     * 思路：借助栈的先进后出特性
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * @param head
     * @return
     */
    public int[] reversePrint_S2(ListNode head) {
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
     * 类似于递归逆序打印链表
     *
     * @param head
     */
    public int[] reversePrint_S3(ListNode head) {
        //1.统计链表元素个数
        int length = 0;
        ListNode cur = head;
        while (cur != null){
            length++;
            cur = cur.next;
        }

        //2.逆序打印
        int[] arr = new int[length];
        reversePrintHelper(head,arr,length-1);
        return arr;
    }

    private void reversePrintHelper(ListNode head,int[] arr,int index){
        if (head == null)
            return;
        reversePrintHelper(head.next,arr,index-1);
        arr[index] = head.val;
    }

    /**
     * 思路：先翻转链表，在遍历
     * @param head
     * @return
     */
    public int[] reversePrint_S4(ListNode head){
        return null;
    }

    //反转链表-递归方式
    private ListNode reverseList(ListNode head){
        if (head.next == null) return head;
        ListNode temp = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return temp;
    }

    private ListNode reverseListNonRecursive(ListNode head){
        ListNode pre = null;
        while (head !=null){
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }


    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = null;
        Offer_06_ReversePrint offer_06_reversePrint = new Offer_06_ReversePrint();
//        int[] result = offer_06_reversePrint.reversePrint_S3(node1);
//        Arrays.stream(result).forEach(System.out::println);
        ListNode tail = offer_06_reversePrint.reverseListNonRecursive(node1);
        while (tail != null){
            System.out.println(tail.val);
            tail = tail.next;
        }

    }
}
