# Groups
Tool to make up spontanous workgroups based on studentlists. No. students per group can be selected.

30jan17 (rhh) Initial version. Data represented by simple String-array, display by JTable (no. rows dynamically adjusted)
23feb17 (ah)  Error hardening, student loading and absent student handling added
04feb18 (rhh) Changed structure -> MVC, Student-class (to handle name &active-state)



Todo:
o GUI beautification...
	- rework of user interface (integration of typical use-cases: class reedit (latecomers), ...)
  - initial window size does not fit -> layout-manager?
  - checkboxes have no color-scheme
  - display of group-name somewhere (filename -> titlebar?)
  - "groupsize" textbox -> picker, dropdownbox, o.a.
  
o MWP design
  - communication concept between layers missing
  - consistent error handling missing

o translation to PHP o.a. ???
