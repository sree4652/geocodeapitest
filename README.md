This repo is a java based project and uses testng to run tests

Prerequisites: make sure you have atleast java17 installed in your machine

How to run:
1.Load the program in any IDE which supports java like IntelliJ

 a) To run user input test
 
 1) Goto Main method(path: /src/main/java/org/example/Main.java )
 2) Run main method by clicking run button or right click and you will see an option called Run Main.main()
 3) It will ask you to give your input in below Run window and click enter(return in mac)
 4) Please give your input and it will print the response

    ![Screenshot 2025-02-23 at 1 36 52 PM](https://github.com/user-attachments/assets/668896d9-6fb2-4189-b01e-4d36deab7a8f)


 b) To run my regression tests
 
 1) Goto testng.xml file which is in root level
 2) Right click on testng.xml file and click Run

 
 ![Screenshot 2025-02-23 at 1 47 06 PM](https://github.com/user-attachments/assets/a5101186-e068-4b74-ad6c-db75c26e544d)

Note: if code gives any error with apikey, please update value in  GeoCodeApiUtils class
path: src/main/java/org/example/utils/GeoCodeApiUtil.java


 Cautious;
Lets say if you still not able to see any run for main method, make sure you goto run/debug configurations and add appliaction with below 

![Screenshot 2025-02-23 at 1 55 20 PM](https://github.com/user-attachments/assets/6a51f54b-68e6-463c-9152-8fd29cb14274)



