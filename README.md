# Groups
Tool to make up spontanous workgroups based on studentlists. No. students per group can be selected.

30jan17 (rhh) Initial version. Data represented by simple String-array, display by JTable (no. rows dynamically adjusted)
23feb17 (ah)  Error hardening, student loading and absent student handling added
04feb18 (rhh) Changed structure -> MVC, Student-class (to handle name &active-state)
05feb18 (rhh) "student active" editing -> modal JDialog
08feb18 (rhh) 
	- Shuffle- separated from split-functionality, 
	- course name added, 
	- latecomers are moved to end of list (easier group integration!), 
	- controller effectively is a presenter now...



Todo:
o GUI beautification...
  - checkboxes in course-edit-view have no color-scheme
  - "groupsize" textbox -> picker, dropdownbox, o.a.
  
o MWP design	
  - consistent error handling missing
  - communication scheme -> publish & subscribe ? (too much effort?)

o translation to PHP o.a. ???
