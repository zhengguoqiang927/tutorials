package com.zhengguoqiang;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int count_bits = Integer.SIZE - 3;
        int capacity = (1 << count_bits) - 1;
        System.out.println(Integer.toBinaryString(capacity));
        System.out.println( "Hello World!" );
    }
}
