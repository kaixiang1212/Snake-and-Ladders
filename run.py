from routes import app
import socket               # Import socket module
'''
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)         # Create a socket object
host = socket.gethostname() # Get local machine name
port = 5044         # Reserve a port for your service.
host = "localhost"
s.connect((host, port))
print("Connected1")
print("pew")
'''
'''
while (True):
# print (s.recv(port))
    # print(host)
    x = input("input")
    s.send(x.encode() + b"\n")
    #s.send(b"message processed..\n")
'''
def sendMessage(message):
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)  # Create a socket object
	host = socket.gethostname()  # Get local machine name
	port = 500# Reserve a port for your service.
	host = "localhost"
	s.connect((host, port))
	print("Connected1")
	print("sending")
	print(s)
	s.send(message.encode()+ b"\n")
	#x = input("input")
	#s.send(x.encode() + b"\n")
	print("sent")

#s.close
print("about to run app")
if __name__ == '__main__':
	app.run(debug=True ,  use_reloader=False, host='0.0.0.0')