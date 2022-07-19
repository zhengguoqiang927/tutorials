package com.zhengguoqiang.offer;

/**
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "We are happy."
 * 输出："We%20are%20happy."
 * <p>
 * 限制：
 * <p>
 * 0 <= s 的长度 <= 10000
 */
public class Offer_05_ReplaceSpace {

    public static void main(String[] args) {
        Offer_05_ReplaceSpace space = new Offer_05_ReplaceSpace();
        String result = space.replaceString_S2("We are happy.");
        System.out.println(result);
    }

    /**
     * 方案一
     * 第一遍遍历统计出空格字符的数量，然后算出新字符数组的长度；第二遍遍历copy字符
     * 也可以只遍历一遍，新字符数组的长度直接等于字符串的长度 x 3，只是会多浪费一点空间
     *
     * 时间复杂度：O(N)
     * 空间复杂度：O(N)
     *
     * @param s
     * @return
     */
    public String replaceSpace_S1(String s) {
        char[] chars = s.toCharArray();
        int spaceNum = 0;
        for (char c:chars){
            if (c == ' ') spaceNum++;
        }

        int newLength = chars.length + spaceNum * 2;
        char[] newChars = new char[newLength];
        for(int i = chars.length-1,m = newLength-1;i>=0;i--){
            if (chars[i] == ' '){
                newChars[m--] = '0';
                newChars[m--] = '2';
                newChars[m--] = '%';
            }else {
                newChars[m--] = chars[i];
            }
        }
        return new String(newChars);
    }

    /**
     * Java和Python的字符串是不可变类型，所以无法实现原地更改，只能新建一个字符串
     *
     * @param s
     * @return
     */
    public String replaceString_S2(String s){
        StringBuilder sb = new StringBuilder();
        for (char c:s.toCharArray()){
            if (c == ' '){
                sb.append("%20");
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
