# ExaservOne_IS_AD
Exaserv One: Intelligent Services – Active Directory
---------------------------------------------------------------------------

EC – Active Directory integrations are very common in all size segments. We want to create an integration package that can be deployed at different customers and configured without going through a painful implementation effort.
We will leverage the intelligent services feature of Employee Central to listen HIRE/TERMINATION events and send to our integration package that will process and forward to Active Directory Systems. 


-> It is key to create a flexible iflow that allow meeting different customer requirements. Flexibility identified in:

SF to HCI:
•	Option 1: Import event from Intelligent Services
•	Option 2: Connect to SFAPI to get all changes since the last run.
•	Import different portlets from EC. External parameters.
HCI to AD:
•	Option1 Post via AD Web Service.
•	Option 2: Flat File in FTP server with and without PGP encryption.
AD to HCI:
•	Option1 Listen AD event posted by AD (https).
•	Option 2: Pick up file from an FTP server.
HCI to SF:
•	Update info via ODATA API
