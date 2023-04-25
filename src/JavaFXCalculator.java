/**
 * @author Imani Lamla 
 * GitHub URL: https://github.com/ImaniLamla/JavaFX_Calculator.git
 * JavaFXCalculator <br>
 * A simple calculator 
 *
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
 * Creates a new JavaFX text field where the numbers will be displayed 
 */
private TextField tfDisplay;    
   
 /**
 * Creates a new JavaFX text where the memory value will be displayed
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

   // Event handler for all the 24 Buttons
   EventHandler<ActionEvent> handler = evt -> {
      String currentBtnLabel = ((Button)evt.getSource()).getText();
      double temp = 0.0;
      switch (currentBtnLabel) {
         // Number buttons
         case "0": case "1": case "2": case "3": case "4":
         case "5": case "6": case "7": case "8": case "9": case ".":
            if (inStr.equals("0")) {
               inStr = currentBtnLabel;  // no leading zero
            } else {
               inStr += currentBtnLabel; // append input digit
            }
            tfDisplay.setText(inStr);
            // Clear buffer if last operator is '='
            if (lastOperator == '=') {
               result = 0;
               lastOperator = ' ';
            }
            break;

         // Operator buttons: '+', '-', 'x', '/' and '='
         case "+":
            compute();
            lastOperator = '+';
            break;
         case "-":
            compute();
            lastOperator = '-';
            break;
         case "\u00D7":
            compute();
            lastOperator = '*';
            break;
         case "\u00F7":
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
        	this.result = Math.sqrt(this.result);
        	this.inStr = this.result + "";
        	
        	this.tfDisplay.setText(this.inStr);
        	lastOperator = '=';
        	break; 
        	
         case "=":
            compute();
            lastOperator = '=';
            break;
            
         //Memory plus   
         case "M+": 
        	if (lastOperator != '=') {
        		 temp = Double.parseDouble(this.inStr);
        		 this.memory += temp;
        	}else 
        		 this.memory += this.result; 
        	 
        	memoryDisplay.setText("Memory = " + this.memory);
        	break;
        	 
         //Memory minus	 
         case "M-":
        	if (lastOperator != '=') {
        		 temp = Double.parseDouble(this.inStr);
        		 this.memory -= temp;
        	}else 
        		 this.memory -= this.result; 
        	 
        	memoryDisplay.setText("Memory = " + this.memory);
        	break;
         
         //Memory recall
         case "MR":
        	this.inStr = String.valueOf(this.memory);
        	tfDisplay.setText(this.memory + "");
        	break;
         
         //Memory Clear
         case "MC":
        	this.memory = 0.0;
        	memoryDisplay.setText("Memory = " + this.memory);
        	break;
        	 
         //Backspace 
         case "\u2190":
        	if (this.inStr.length() == 1)
        		 this.inStr = "0";
        	else 
        		 this.inStr = inStr.substring(0, inStr.length() - 1);
        	 
        	this.tfDisplay.setText(this.inStr);
        	break;

         // Clear button
         case "C":
            result = 0;
            inStr = "0";
            lastOperator = ' ';
            tfDisplay.setText("0");
            break;
      }
   };

   // User pushes '+', '-', '*', '/' or '=' button.
   // Perform computation on the previous result and the current input number,
   // based on the previous operator.
   private void compute() {
      double inNum = Double.parseDouble(inStr);
      inStr = "0";
      if (lastOperator == ' ') {
         result = inNum;
      } else if (lastOperator == '+') {
         result += inNum;
      } else if (lastOperator == '-') {
         result -= inNum;
      } else if (lastOperator == '*') {
         result *= inNum;
      } else if (lastOperator == '/') {
         result /= inNum;
      } else if (lastOperator == '^') {
    	 result = Math.pow(this.result, inNum);
      } else if (lastOperator == '=') {
         // Keep the result for the next operation
      }
      tfDisplay.setText(result + "");
   }

   // Setup the UI
   @Override
   public void start(Stage primaryStage) {
	   
	   Button[] btns = new Button[24];;          // 24 buttons
	   String[] btnLabels = {  // Labels of 24 buttons
	      "7", "8", "9", "+",
	      "4", "5", "6", "-",
	      "1", "2", "3", "\u00D7",
	      ".", "0", "\u2190", "\u00F7",
	      "C", "^", "\u221A", "=",
	      "M+", "M-", "MR", "MC"
	   };
      // Setup the Display TextField
      tfDisplay = new TextField("0");
      tfDisplay.setEditable(false);
      tfDisplay.setAlignment(Pos.CENTER_RIGHT);
      
      //Set up the text 
      memoryDisplay = new Text("Memory = 0.0");

      // Setup a GridPane 
      int numCols = 4;
      GridPane paneButton = new GridPane();
      paneButton.setPadding(new Insets(15, 0, 15, 0));  // top, right, bottom, left
      paneButton.setVgap(5);  // Vertical gap between nodes
      paneButton.setHgap(5);  // Horizontal gap between nodes
      // Setup 4 columns of equal width, fill parent
      ColumnConstraints[] columns = new ColumnConstraints[numCols];
      for (int i = 0; i < numCols; ++i) {
         columns[i] = new ColumnConstraints();
         columns[i].setHgrow(Priority.ALWAYS) ;  // Allow column to grow
         columns[i].setFillWidth(true);  // Ask nodes to fill space for column
         paneButton.getColumnConstraints().add(columns[i]);
      }

      // Setup 24 Buttons and add to GridPane; and event handler
      
      for (int i = 0; i < btns.length; ++i) {
         btns[i] = new Button(btnLabels[i]);
         btns[i].setOnAction(handler);  // Register event handler
         btns[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // full-width
         paneButton.add(btns[i], i % numCols, i / numCols);  // control, col, row
         
         // to change button colours
          switch(btnLabels[i]){
           	case "M+": case "M-": case "MR": case "MC":
          		btns[i].setStyle("-fx-color: blue");
          		break;
          	case "+": case "-": case "\u00D7": case "\u00F7": case "C": case "^": case "\u221A": case "=":
          		btns[i].setStyle("-fx-color: orange");
          		break;
          }//end switch 
          
      }

      // Setup up the scene graph rooted at a BorderPane (of 5 zones)
      BorderPane root = new BorderPane();
      root.setPadding(new Insets(15, 15, 15, 15));  // top, right, bottom, left
      root.setTop(tfDisplay);     // Top zone contains the TextField
      root.setCenter(paneButton); // Center zone contains the GridPane of Buttons
      root.setBottom(memoryDisplay); //Bottom zone contains the memory text

      // Set up scene and stage
      primaryStage.setScene(new Scene(root, 300, 320));
      primaryStage.setTitle("JavaFX Calculator");
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
