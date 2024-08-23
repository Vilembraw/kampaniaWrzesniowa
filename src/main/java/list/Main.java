package list;

import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        {
            CustomList<Integer> list = new CustomList<>();
            list.addLast(1);
            list.addLast(2);
            list.addLast(3);
            list.addLast(4);

//            System.out.println(list.getFirst());
//            System.out.println(list.getLast());

//            System.out.println(list.removeFirst());
//            System.out.println(list.removeLast());
//            System.out.println(list.size());

//            for (int i = 0; i < list.size(); i++)
//                System.out.print(list.get(i));

            for(Integer i : list){
                System.out.println(i);
            }

            System.out.println();
            list.stream()
                    .map(i -> i+1)
                    .forEach(System.out::println);
        }

    }
}
