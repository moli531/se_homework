# 作业要求

* 使用 Github Action 或其他CI 服务，对你的仓库进行简单的构建、测试

  在Github仓库里面新建一个 **.github/workflows** 目录，再新建一个 **demo.yml** 文件，并修改demo.yml的run路径为：

  ```java
  run: cd ${{ github.workspace }}/hw1/recursionBinarySearch
  ```

  

  * 运行截图

  ![image-20220530223520635](https://raw.githubusercontent.com/moli531/img/main/202205302235210.png)

  ![image-20220530215605746](https://raw.githubusercontent.com/moli531/img/main/202205302156396.png)

  ![image-20220530215310073](https://raw.githubusercontent.com/moli531/img/main/202205302153805.png)![image-20220530223653826](https://raw.githubusercontent.com/moli531/img/main/202205302236973.png)