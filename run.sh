#!/bin/bash

# Function to run SingleProcessChat
run_single_process_chat() {
    echo "Running Single Process Chat..."
    java -cp target/classes org.yura.single.process.chat.SingleProcessChat
}

# Function to run MultiProcessesChat
run_multi_processes_chat() {
    echo "Running Multi Processes Chat..."
    java -cp target/classes org.yura.multi.processes.chat.MultiProcessesChat
}

# Prompt the user for input
echo "Select the Application Mode:"
echo "1) Single Process Chat"
echo "2) Multi Processes Chat (One server / Two clients)"
read -p "Enter choice [1-2]: " choice

# Run the selected application
case $choice in
    1)
        run_single_process_chat
        ;;
    2)
        run_multi_processes_chat
        ;;
    *)
        echo "Invalid choice!"
        exit 1
        ;;
esac
