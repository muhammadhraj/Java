import static org.junit.Assert.*;

import org.junit.Test;


public class SimpleGivenTests
{
   @Test
   public void oneStudent()
   {
      MaxHeap heap = new MaxHeap(10);
      heap.insert(new Student("Susan", 3.5, 60));
      assertEquals(3.5, heap.extractMax().gpa(), .000001);
      assertEquals(0, heap.size());
   }

   @Test
   public void aInsertAFewStudents()
   {
      MaxHeap heap = new MaxHeap(10);
      heap.insert(new Student("Susan", 3.5, 60));
      heap.insert(new Student("Ben", 3.4, 70));
      heap.insert(new Student("Reed", 4.0, 120));
      heap.insert(new Student("Johnny", 1.2, 50));
      assertEquals(4.0, heap.extractMax().gpa(), .000001);
      assertEquals(3.5, heap.extractMax().gpa(), .000001);
      heap.insert(new Student("Billy", 2.7, 20));
      assertEquals(3.4, heap.extractMax().gpa(), .000001);
      assertEquals(2.7, heap.extractMax().gpa(), .000001);
      assertEquals(1.2, heap.extractMax().gpa(), .000001);
   }

   @Test
   public void exceptionTest()
   {
      MaxHeap heap = new MaxHeap(10);
      heap.insert(new Student("Ben", 3.4, 70));
      assertEquals(3.4, heap.extractMax().gpa(), .000001);
      try {
    	  heap.extractMax();
    	  fail("You shouldn't reach this line, an IndexOutOfBoundsException should have been thrown.");
      } catch (IndexOutOfBoundsException except) {
    	  assertEquals(except.getMessage(), "No maximum value:  the heap is empty.");
      }

   }
   
   @Test
   public void changeKeyTest()
   {
	   MaxHeap heap = new MaxHeap(10);
	   Student susan = new Student("Susan", 3, 6);
	   Student ben = new Student("Ben", 2.4, 10);
	   Student reed = new Student("Reed", 3.3, 3);
	   Student johnny = new Student("Johnny", 1, 4);
	   heap.insert(susan);;
	   heap.insert(ben);
	   heap.insert(johnny);
	   heap.insert(reed);
	   assertEquals(reed, heap.getMax());
	   heap.addGrade(susan, 4, 3);  //should give her a 3.333333333 gpa
	   assertEquals(susan, heap.getMax());
	   assertEquals(3.33333333, heap.extractMax().gpa(), .000001);
	   heap.addGrade(reed, .7, 3);  //should give him a 2.0
	   heap.addGrade(johnny,  4,  4);  //should give him a 2.5
	   assertEquals(2.5, heap.extractMax().gpa(), .000001);
	   assertEquals(2.4, heap.extractMax().gpa(), .000001);
	   assertEquals(2.0, heap.extractMax().gpa(), .000001);
   }
   
}