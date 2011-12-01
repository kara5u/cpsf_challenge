#
# makefile.
#

PACKAGE= ls
NAME= lsl
SRCS= $(PACKAGE).c
HEADS= $(PACKAGE).h
OBJS= $(SRCS:.c=.o)

# common (*.o)
LD= gcc
LDLIBS= -lm

# *.c
CC = gcc

# run
$(PACKAGE):$(OBJS)
	$(CC) -o $(NAME) -g -O0 $(SRCS)
