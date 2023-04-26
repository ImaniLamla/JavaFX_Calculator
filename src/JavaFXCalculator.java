/**
 * @author Imani Lamla 
 * GitHub URL: https://github.com/ImaniLamla/JavaFX_Calculator.git
 * JavaFXCalculator <br>
 * A simple calculator using JavaFX
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * The class that contains all the methods necessary for the calculator to work.
 */
public class JavaFXCalculator extends Application {

	
/**
 * Declares a new JavaFX text field where the numbers will be displayed 
 */
private TextField tfDisplay;    
   
 /**
 * Declares a new JavaFX text where the memory value will be displayed
 */
private Text memoryDisplay;	
   
 /**
 * Stores the current memory value
 */
private double memory = 0.0;	
   
 /**
 * Stores the result of the calculations
 */
private double result = 0.0;      
   
 /**
 * Stores the user's input as a string
 */
private String inStr = "0";  
   
 /**
 * Stores the last operator used by the user
 */
private char lastOperator = ' ';

  /**
   * Event handler for all the 24 Buttons
   */
   EventHandler<ActionEvent> handler = evt -> {
      String currentBtnLabel = ((Button)evt.getSource()).getText();
      
      double temp = 0.0;
      
      switch (currentBtnLabel) {
      
         /*Number buttons*/
         case "0": case "1": case "2": case "3": case "4":
         case "5": case "6": case "7": case "8": case "9": case ".":
            if (inStr.equals("0")) {
               inStr = currentBtnLabel;  	//No leading zero
            } else {
               inStr += currentBtnLabel; 	//Appends input digit
            }
            tfDisplay.setText(inStr);		//Displays inStr in the text field
            
            
            if (lastOperator == '=') {		//Clears buffer if last operator is '='
               result = 0;
               lastOperator = ' ';
            }
            break;

         /*Operator buttons: '+', '-', 'x', '/' and '='*/
         case "+":					//addition
            compute();
            lastOperator = '+';
            break;
         case "-":					//subtraction
            compute();
            lastOperator = '-';
            break;
         case "\u00D7":				//multiplication
            compute();
            lastOperator = '*';
            break;
         case "\u00F7":				//division
            compute();
            lastOperator = '/';
            break;
         case "^":					//power
        	compute();
        	lastOperator = '^';
        	break;
         case "\u221A":				//square root
        	if (lastOperator != '=') {
        		this.result = Double.parseDouble(inStr);
        	}
        	this.result = Math.sqrt(this.result);		//Uses the Math.sqrt method to calculate the square root
        	this.inStr = this.result + "";
        	
        	this.tfDisplay.setText(this.inStr);			//Displays the result in the text field
        	lastOperator = '=';
        	break; 
        	
         case "=":					//equals
            compute();
            lastOperator = '=';
            break;
              
         case "M+": 				//Memory plus 
        	if (lastOperator != '=') {							//Test to see if the last operator is not =
        		 temp = Double.parseDouble(this.inStr);
        		 this.memory += temp;							//Appends temp to the current memory value
        	}else 
        		 this.memory += this.result; 					//Appends the result of the calculation to the current memory value
        	 
        	memoryDisplay.setText("Memory = " + this.memory);	//Updates the JavaFX text control with the new memory value
        	break;
        	 
         case "M-":					//Memory minus				Similar to M+ but it subtracts from the memory value
        	if (lastOperator != '=') {
        		 temp = Double.parseDouble(this.inStr);
        		 this.memory -= temp;
        	}else 
        		 this.memory -= this.result; 
        	 
        	memoryDisplay.setText("Memory = " + this.memory);
        	break;
         
         case "MR":					//Memory recall
        	this.inStr = String.valueOf(this.memory);				//Converts double to a string
        	tfDisplay.setText(this.memory + "");					//Updates the text control
        	break;
         
         case "MC":					//Memory Clear
        	this.memory = 0.0;										//Resets the memory
        	memoryDisplay.setText("Memory = " + this.memory);
        	break;
        	 
         case "\u2190":				//Backspace 
        	if (this.inStr.length() == 1)
        		 this.inStr = "0";										//Returns 0 if the string length is 1
        	else 
        		 this.inStr = inStr.substring(0, inStr.length() - 1);	//Returns the same string but removes the farthest char on the right
        	 
        	this.tfDisplay.setText(this.inStr);
        	break;

         case "C":					//Clear: resets all values 
            result = 0;
            inStr = "0";
            lastOperator = ' ';
            tfDisplay.setText("0");
            break;
      }
   };

   /**
    * Performs calculations on the previous result and current input number based on the <br>
    * last operator used. <br>
    * Runs when the user pushes either of the math operator buttons except the Square root <br>
    * button.
    */
    private void compute() {
    	
      double inNum = Double.parseDouble(inStr);	//Stores converted string into double
      inStr = "0";
      
      if (lastOperator == ' ') {
         result = inNum;
      } else if (lastOperator == '+') {			//Performs addition calculation
         result += inNum;
      } else if (lastOperator == '-') {			//Performs subtraction calculation
         result -= inNum;
      } else if (lastOperator == '*') {			//Performs multiplication calculation
         result *= inNum;
      } else if (lastOperator == '/') {			//Performs division calculation
         result /= inNum;
      } else if (lastOperator == '^') {			//Performs power calculation
    	 result = Math.pow(this.result, inNum); //Uses the Math.pow function	
      } else if (lastOperator == '=') {
         // Keep the result for the next operation
      }
      tfDisplay.setText(result + "");			//Displays the result in the text field
   }//end of compute method

  /**
   * Sets up the GUI
   */
   @Override
   public void start(Stage primaryStage) {
	   
	   Button[] btns = new Button[24];		//Creates an array of 24 buttons
	   String[] btnLabels = {  				//Creates an array of 24 button labels
	      "7", "8", "9", "+",
	      "4", "5", "6", "-",
	      "1", "2", "3", "\u00D7",
	      ".", "0", "\u2190", "\u00F7",
	      "C", "^", "\u221A", "=",
	      "M+", "M-", "MR", "MC"
	   };
	   
      /*Setups the display TextField*/
      tfDisplay = new TextField("0");			//Instantiates a new JavaFX text field with its display message
      tfDisplay.setEditable(false);				//Text cannot be edited via the text field
      tfDisplay.setAlignment(Pos.CENTER_RIGHT); 
      
      /*Setups the display Text*/
      memoryDisplay = new Text("Memory = 0.0"); //Instantiates a new JavaFX text with its display message

      /*Setup a GridPane*/
      int numCols = 4;
      GridPane paneButton = new GridPane();
      paneButton.setPadding(new Insets(15, 0, 15, 0));  //Top, right, bottom, left
      paneButton.setVgap(5);  							//Vertical gap between nodes
      paneButton.setHgap(5);  							//Horizontal gap between nodes
      
      /*Setups 4 columns of equal width, fill parent*/
      ColumnConstraints[] columns = new ColumnConstraints[numCols];
      
      for (int i = 0; i < numCols; ++i) {
         columns[i] = new ColumnConstraints();
         columns[i].setHgrow(Priority.ALWAYS) ;  			//Allow column to grow
         columns[i].setFillWidth(true);  					//Ask nodes to fill space for column
         paneButton.getColumnConstraints().add(columns[i]);
      }

      /*Setups 24 Buttons and adds them to the GridPane and event handler*/
      for (int i = 0; i < btns.length; ++i) {
         btns[i] = new Button(btnLabels[i]);
         btns[i].setOnAction(handler);  						  //Register event handler
         btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  //Full-width
         paneButton.add(btns[i], i % numCols, i / numCols);       //Control, column, row
         
         /*Changes buttons' colors*/
          switch(btnLabels[i]){
           	case "M+": case "M-": case "MR": case "MC": 		//Set to blue
          		btns[i].setStyle("-fx-color: blue");
          		break;
          		
          	case "+": case "-": case "\u00D7": case "\u00F7":   //Set to orange
          	case "C": case "^": case "\u221A": case "=":
          		btns[i].setStyle("-fx-color: orange");
          		break;
          }//end switch 
      }//end for loop

      /*Setups the scene graph rooted at a BorderPane (of 5 zones)*/
      BorderPane root = new BorderPane();
      root.setPadding(new Insets(15, 15, 15, 15));  //Top, right, bottom, left
      root.setTop(tfDisplay);     					//Top zone contains the TextField
      root.setCenter(paneButton); 					//Center zone contains the GridPane of Buttons
      root.setBottom(memoryDisplay); 				//Bottom zone contains the memory text

      /*Setups scene and stage*/
      primaryStage.setScene(new Scene(root, 300, 320));
      primaryStage.setTitle("JavaFX Calculator");
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }//end main
   
}//end class
