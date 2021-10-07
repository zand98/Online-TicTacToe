package generic;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class Stack<T> extends ArrayList<T>{
    
    public T pop()
    {
        if(!isEmpty())
        {
           return remove(size()-1);
        }
        else
            throw new EmptyStackException();
    }
    
    public void push(T value)
    {
        add(value); 
    }
    
    public void display(Stack value)
    {
        if(!isEmpty())
        System.out.println(value);
    }
    public int getElement(Stack value,int index)
    {
        return  (int) value.get(index);
        
    }
//    public static void main(String[] args) {
//        Stack s=new Stack();
////        s.display(s);
//        s.push(1);
//        s.push(41);
//        s.push(10);
//        s.push(71);
//       s.display(s);
//       
//        System.out.println(s.pop());
//        System.out.println(s.pop());
//        System.out.println(s.pop());
////        System.out.println(s.get(s.size()-1));
////        System.out.println(s.get(s.size()-4));
////        System.out.println(s.get(s.size()-2));
////        System.out.println(s.pop(s));
////        System.out.println(s.pop(s));
////        System.out.println(s.pop(s));
//     }   
}