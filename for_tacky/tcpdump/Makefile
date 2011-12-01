#
# makefile.
#

PACKAGE= packet_scanner
NAME= packet_scanner
SRCS= $(PACKAGE).c
HEADS= $(PACKAGE).h
OBJS= $(SRCS:.c=.o)

# common (*.o)
LD= gcc
LDLIBS= -lpcap

# *.c
CC = gcc

# run
$(PACKAGE):$(OBJS)
	$(CC) -o $(NAME) -g -O0 $(LDLIBS) $(SRCS)
