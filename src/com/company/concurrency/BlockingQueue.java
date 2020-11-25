package com.company.concurrency;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueue {
    //阻塞队列长度
    private static int QUEUE_SIZE = 1000;
    //搜索最大线程数
    private static int SEARCH_THREAD_SIZE = 80;
    private static ArrayBlockingQueue<File> queue = new ArrayBlockingQueue(QUEUE_SIZE);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入路径（e.g. C:/,若直接回车则搜索全路径）:");
        String source = scanner.nextLine();
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("请输入文件名关键字:");
        String keyWord = scanner1.nextLine();
        System.out.println("---------搜索结果如下----------");
        if("".equals(source)){
            source = "C:/,D:/,E:/,F:/,G:/,H:/,I:/,J:/,K:/,L:/,M:/,N:/";
        }
        String[] split = source.split(",");
        for(int j=0;j<split.length;j++){
            File file = new File(split[j]);

            File[] files = file.listFiles();
            if(files!=null){

                for(int i=0;i<files.length;i++){
                    File file1 = files[i];
                    new Thread(()->{
                        addQueue(file1);
                    }).start();
                }

            }


        }




        for(int i=0;i<SEARCH_THREAD_SIZE;i++){
            new Thread(()->{
                searchKeyWord(keyWord);

            }).start();
        }


    }

    private static void searchKeyWord(String keyWord) {
        Boolean isHave = true;
        try {
            while (isHave){
                File take = queue.poll(2, TimeUnit.SECONDS);
                if(take==null){
                    isHave = false;
                }

                if(take!=null&&take.getName().indexOf(keyWord)>-1){
                    System.out.println(take.getAbsolutePath());
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void addQueue(File source){
        File[] files = source.listFiles();
        if(files!=null){
            for (File f:files
            ) {

                if(f!=null&&f.isDirectory()){
                    addQueue(f);
                }else{
                    try {
                       // System.out.println(f.getName()+"进入QUEUE");
                        queue.put(f);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }



    }
}
