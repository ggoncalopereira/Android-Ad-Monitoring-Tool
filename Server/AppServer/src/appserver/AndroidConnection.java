/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.util.TreeSet;

/**
 *
 * @author JPVS
 */
public class AndroidConnection extends Thread{

    TreeSet<String> urls;
    
    public AndroidConnection(TreeSet<String> urls) {
        this.urls= urls;
    }
    
    @Override
    public void run(){
        
    }
    
}
