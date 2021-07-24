# MinlyAndroidApp

# Minly-Task


Description
------------
Android App: Make user uploade image to the local server and get all image uploaded.
I'm use MVVM arcitecher and repositry batern to make code cleane ande more sepration. 
project divide to 4 main layers :
1- network.
2- shared.
3- data.
4- ui.

network layer : it responspel on end point interface and creat the network client.

shared layer : contain apiWrapper and helpers ( apiWrapper - > is sepration layer to handel network call status).

Data layer : contain repositry and the resposeModel .

ui : contain the business.

Instaliation 
------------
Need Android Studio IDE.
Change BASE_URL in class NetworkClient to your local host.
