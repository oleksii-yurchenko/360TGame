#!/bin/bash

# Function to compile the Maven project
compile_maven_project() {
    echo "Compiling Maven project..."
    mvn compile
    if [ $? -ne 0 ]; then
        echo "Maven project compilation failed!"
        exit 1
    fi
    echo "Maven project compiled successfully."
}

# Function to run SingleProcessChat
run_single_process_chat() {
    echo "Running Single Process Chat..."
    java -cp target/classes org.yura.single.process.chat.SingleProcessChat
}

# Function to run TwoProcessesChat
run_two_processes_chat() {
    echo "Running Two Processes Chat..."
    java -cp target/classes org.yura.two.processes.chat.TwoProcessesChat
}

# Compile the Maven project first
compile_maven_project

# Prompt the user for input
echo "Select the Application Mode:"
echo "1) Single Process Chat"
echo "2) Two Processes Chat"
read -p "Enter choice [1-2]: " choice

# Run the selected application
case $choice in
    1)
        run_single_process_chat
        ;;
    2)
        run_two_processes_chat
        ;;
    *)
        echo "Invalid choice!"
        exit 1
        ;;
esac
