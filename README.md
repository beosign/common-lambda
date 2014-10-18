common-lambda
=============

Project with helper classes for dealing with lambdas.

Features
--------
*   **Checked variants of Consumer and Function**<br/>
    When using lambdas, checked exceptions cannot be used easily. Using a small wrapper, this becomes possible:<br/>
    <code> 
    List<String> strings = Arrays.asList("Hello", "World", "Test");
    strings.stream().map(CheckedFunction.convert(s -> new FileInputStream(s)));
    </code>
    
*   **Processors for parallel work**<br/>
	During the execution of lambdas exceptions may occur which cause the execution to stop. However, maybe one just wants to continue processing the next item, storing the caught exceptions and using them later. 
	`AbstractWorkProcessor` and `DefaultWorkProcessor` can be used in this scenario.
