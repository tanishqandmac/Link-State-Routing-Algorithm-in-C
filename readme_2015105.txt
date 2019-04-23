Given a network topology in a given format as shown here: 1:2, 10; 2:3, 20; 1:3:5; the above string represents a network of 3 nodes where node 1 and 2 are connected with a link cost of 10 and so on. My implementation allows to be able to calculate the shortest path at each node and also receive a change in the cost of a link and then recalculate. All the intermediate steps are shown in the output. Moreover for a network of 3 nodes poison reverse has also been implemented.

Instructions to input data (Example):
 A:B, 1; B:C, 3; A:C, 5; C:D, 4; D:E, 2; B:E, 9; C:F, 10; E:G, 2;

P.S - the input must follow the above template (including the spaces) inorder to successfully calculate forwarding tables