/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sofia
 */
public class LinkedStack implements Stack{

    private Node top; //apuntador al tope de la pila
    private int counter; //contador de elementos apilados

    public LinkedStack() {
        this.top = null;
        this.counter = 0;
    }
    
    @Override
    public int size() {
        return counter;
    }

    @Override
    public void clear() {
        this.top  =null;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public Object peek() throws StackException {
        if(isEmpty())
            throw new StackException("Linked Stack is empty");
        return top.data;
    }

    @Override
    public Object top() throws StackException {
        if(isEmpty())
            throw new StackException("Linked Stack is empty");
        return top.data;
    }

    @Override
    public void push(Object element) throws StackException {
        Node newNode = new Node(element);
        if(isEmpty()){
            //crear un nuevo nodo
            this.top = newNode;
        }else{
            newNode.next  =top; //hacemos el enlace entre nodos
            top = newNode;//le decimos que apunte a newNode
        }
        this.counter++;//incremento el contador
    }

    @Override
    public Object pop() throws StackException {
       if (isEmpty()) 
            throw new StackException("Linked Stack is empty");
        Object element = top.data;
        top = top.next;//movemos top al siguiente nodo
        this.counter--;//decrementamos el contador
        return element;
    }
    
    @Override
    public String toString() {
        String result = "Linked Stack content: \n";
        try {
            LinkedStack auxStack = new LinkedStack();
            while(!isEmpty()){
                    result+=peek()+" ";
                    auxStack.push(pop());
            }
            //ahora debemos dejar la pila como al inicio
            while(!auxStack.isEmpty()){
                push(auxStack.pop());
            }
        } catch (StackException ex) {
                Logger.getLogger(ArrayStack.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        return result;
    }
}
