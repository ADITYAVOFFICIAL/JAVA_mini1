import socket

# Create a socket object
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Define the host and port to listen on
host = "127.0.0.1"
port = 12345

# Bind the socket to the host and port
server_socket.bind((host, port))

# Start listening for incoming connections
server_socket.listen()

print(f"Server listening on {host}:{port}")

while True:
    # Accept incoming connections
    client_socket, addr = server_socket.accept()
    print(f"Accepted connection from {addr}")

    while True:
        # Receive and print messages from the client
        data = client_socket.recv(1024).decode('utf-8')
        if not data:
            # Client disconnected
            print(f"Client {addr} disconnected")
            break

        # Split the received message into components
        components = data.split('#')
        if len(components) == 3:
            action, language, text = components

            # Now you can process the received data:
            # - action contains "Correct" or "Translate"
            # - language contains the selected language
            # - text contains the text to be corrected or translated
            print(f"Action: {action}, Language: {language}, Text: {text}")

        # You can send a response back to the client if needed
        # response = "Received: " + data
        response="Hello I'm under the water"+data
        client_socket.send(response.encode('utf-8'))

# Close the client socket - This code will not be reached as the outer loop is infinite
client_socket.close()
