#AWS
HW
1. Create this infrastructure https://hw-rd-bucket-temp.s3.amazonaws.com/hw-infrastructure.png 
using cloudformation and web console or aws cli 
2. Create simple java project that has CRUD operations for simple entity in RDS.
3. Put jar with your pet project into S3 to make it accessible for EC2 user data script.

AC:
Your cloudformation template should create
1. ELB
2. Target group with 2 EC2s. 

If some services require payment, you can skip them.

##Note on homework files:
The result templates stored in "templates" subdirectory:
1. HW16-part-cf.template file contains resources created by using cloudformation(CF) (the resources created by using amazon web console (AWC), can be demonstrated upon request)
2. HW16-all-cf.template contains all the resources involved and is not required by the task but can be used to demonstrate the whole schema, which includes resources created by using both CF and AWC
3. src directory contains simple CRUD project which is uploaded in .jar package on Amazone S3 and used as mentioned in the task. 