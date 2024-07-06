### How to Launch the App?

1. Install **Maven 3.9.8**, **Java 22.0.1** or higher.
2. Check the application config in **src/main/resources/application.properties**:
   
   | Config          | Description                             |
   | --------------- | --------------------------------------- |
   | *msg_limit*     | Limitation for input/output messages count |
   | *timeout*       | Delay between messages in ms            |
   | *start_msg*     | Start message                           |
   | *first_player*  | First player name                       |
   | *second_player* | Second player name                      |
   | *host*          | Host for Socket solution                |
   | *port*          | Port for Socket solution                |

3. Run the entry script:
   ```bash
   ./run.sh
4. Choose **Single Process Chat** or **Three Processes Chat** solutions by pressing 1 or 2 on the keyboard.
