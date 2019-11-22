import socket

import server


class client:
    def __init__(self, _id, player_name):
        self._id = _id
        self.name = player_name
        self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.server.connect((server.host, server.port))
        except Exception:
            raise RuntimeError("Could not connect to server")
        self._ready = False

    def get_id(self):
        return self._id

    def roll(self):
        self.send("roll\n")

    def stop(self):
        self.send("stop\n")

    def next(self):
        self.send("next\n")

    def change(self, name):
        self.name = name
        string = "change " + name + "\n"
        self.send(string)

    def send(self, string):
        self.server.send(bytes(string, 'utf-8'))

    def ready(self):
        self._ready = True

    @property
    def username(self):
        return self.username

    @property
    def is_authenticated(self):
        return True

    @property
    def is_active(self):
        return True

    @property
    def is_anonymous(self):
        return False

    def is_ready(self):
        return self._ready
