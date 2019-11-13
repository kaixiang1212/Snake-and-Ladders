import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(("localhost", 8888))

print("Press q to quit")
line = input("Enter Command: ")
while line != "q" :
    line = line + "\n"
    try:
        s.send(bytes(line, 'utf-8'))
    except :
        print("Server has disconnected")
    line = input("Enter Command: ")
