import serial
import RPi.GPIO as GPIO
import time

# Define the pins for IR sensors
IRSensor1 = 20
IRSensor2 = 21
IRSensor3 = 26
IRSensor4 = 19

# Set GPIO mode
GPIO.setmode(GPIO.BCM)


# Define the Bluetooth serial port
bt_serial = serial.Serial("/dev/ttyAMA0", baudrate=9600, timeout=1)

# Setup GPIO

GPIO.setup(IRSensor1, GPIO.IN)
GPIO.setup(IRSensor2, GPIO.IN)
GPIO.setup(IRSensor3, GPIO.IN)
GPIO.setup(IRSensor4, GPIO.IN)

print("Program started")

try:
    while True:
        # Read IR sensor status
        statusSensor1 = GPIO.input(IRSensor1)
        statusSensor2 = GPIO.input(IRSensor2)
        statusSensor3 = GPIO.input(IRSensor3)
        statusSensor4 = GPIO.input(IRSensor4)

        # Print and send status over Bluetooth
        if statusSensor1 == 0:
            print("1 Parking Full")
            bt_serial.write(b"One\n")
        else:
            print("1 Parking Empty")

        if statusSensor2 == 0:
            print("2 Parking Full")
            bt_serial.write(b"Two\n")
        else:
            print("2 Parking Empty")

        if statusSensor3 == 0:
            print("3 Parking Full")
            bt_serial.write(b"Three\n")
        else:
            print("3 Parking Empty")

        if statusSensor4 == 0:
            print("4 Parking Full")
            bt_serial.write(b"Four\n")
        else:
            print("4 Parking Empty")

        print("................................")
        time.sleep(1)

except KeyboardInterrupt:
    GPIO.cleanup()
    print("Program stopped")
