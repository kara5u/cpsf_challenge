#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netinet/tcp.h>
#include <pcap.h>

#define SIZE_ETHERNET 14
#define ETHER_ADDR_LEN	6

/********************************************************************
 * packet_scanner.c
 * Dumping network packet information (macaddr, port packet length)
 *
 * Author: Yutaka Karatsu
 * Date: 2011.12.1
 * for cpsf challenge
 ********************************************************************/

// ethernet header
struct struct_ethernet {
	u_char  ether_dhost[ETHER_ADDR_LEN];
	u_char  ether_shost[ETHER_ADDR_LEN];
	u_short ether_type;
};

struct struct_ip_header {
    u_char ip_vhl;  // version
    u_char ip_tos;  // service type?
    u_short ip_len; // packet length
    u_short ip_id;  // id
    u_short ip_off; // flagment offset ? 

#define IP_RF 0x8000
#define IP_DF 0x4000
#define IP_MF 0x2000
#define IP_OFFMASK 0x1fff

    u_char ip_ttl; // ttl
    u_char ip_p;   // protocol
    u_short ip_sum;// checksum
    struct in_addr ip_src; // sender ip addr
    struct in_addr ip_dst; // receiver ip addr
};

struct struct_tcp_header {
    u_short th_sport; // sender port
    u_short th_dport; // receiver port 
    tcp_seq th_seq;   // sequence number
    tcp_seq th_ack;   // ack
    u_char th_offx2;  // ?
    u_char th_flags;  // flag?
    u_short th_win;   // window size 
    u_short th_sum;   // check sum
    u_short th_urp;   // ?
};

void scan_loop(u_char *, const struct pcap_pkthdr *, const u_char *packet);
void print_packet_data(const struct struct_ethernet* eh, const struct struct_ip_header* iph, const struct struct_tcp_header* tcph);

/*
 * convert in_addr to char string
 */
void hex_to_char_ip(struct in_addr ina, char* char_ip) {
    unsigned int src;
    memcpy(&src, &ina, sizeof(unsigned int));
    int a = (src >> 24) & 0xFF; // byte to int
    int b = (src >> 16) & 0xFF;
    int c = (src >> 8) & 0xFF;
    int d = (src) & 0xFF;
    sprintf(char_ip, "%d.%d.%d.%d", d,c,b,a);   
}

/*
 * print mac address, port, packet length, ip address
 */
void print_packet_data(const struct struct_ethernet* eh, const struct struct_ip_header* iph, const struct struct_tcp_header* tcph) {
	int i;
    char* sip_addr;
    if((sip_addr = (char*)malloc(16*sizeof(char))) == NULL) {
        return;
    }
    char* dip_addr;
    if((dip_addr = (char*)malloc(16*sizeof(char))) == NULL) {
        return;
    }

    for (i = 0; i < 6; ++i) {
		printf("%02x", (int)eh->ether_shost[i]);
		if(i < 5){
			printf(":");
		}
	}
    printf(" -> ");
    for (i = 0; i < 6; ++i) {
		printf("%02x", (int)eh->ether_dhost[i]);
		if(i < 5){
			printf(":");
		}
	}
	printf("\n");
    
    hex_to_char_ip(iph->ip_src, sip_addr);
    hex_to_char_ip(iph->ip_dst, dip_addr);
    printf("\t ip address: %s -> %s\n", sip_addr, dip_addr);

    printf("\t port: %d -> %d\n", (int)tcph->th_sport, (int)tcph->th_dport);
    printf("\t packet size: %d\n", (int)iph->ip_len);
    free(sip_addr);
    free(dip_addr);
}


main(int argc, char *argv[]) {
	pcap_t *pd;
	int snaplen = 64;
    int pflag = 0;
    int timeout = 1000;
    char ebuf[PCAP_ERRBUF_SIZE];
    char* device;
    bpf_u_int32 localnet, netmask;
    pcap_handler callback;
    struct bpf_program;

    // lookup network device
    device = pcap_lookupdev(ebuf);
    if (device == NULL) {
        fprintf(stderr, "A network device is not found.: %s\n", ebuf);
        exit(1);
    }

    // open
    if ((pd = pcap_open_live(device, snaplen, !pflag, timeout, ebuf)) == NULL) {
        fprintf(stderr, "Can't open the network device.: %s\n", ebuf);
		exit(1);
    }	
    
    // get subnet and mask
	if (pcap_lookupnet(device, &localnet, &netmask, ebuf) < 0) {
        fprintf(stderr, "Can't lookup the device.: %s\n", ebuf);
		exit(1);
    }
    callback = scan_loop;
    if (pcap_loop(pd, -1, callback, NULL) < 0) {
		exit(1);
    }
	pcap_close(pd);
	exit(0);
}

// get header size
#define IP_HL(ip)       (((ip)->ip_vhl) & 0x0f)
#define TH_OFF(th)  (((th)->th_offx2 & 0xf0) >> 4)

void scan_loop(u_char* args, const struct pcap_pkthdr* header, const u_char* packet){
    // パケットを構造体にキャストして各変数にぶっこむ
	const struct struct_ethernet* eh;        
    const struct struct_ip_header* iph;
    const struct struct_tcp_header* tcph;
    u_int size_ip;
    u_int size_tcp;
	int i;
    
    eh = (struct struct_ethernet *)(packet);

    // packetの先頭アドレスからethernet_header_size分インクリメントするとip_header
    iph = (struct struct_ip_header *)(packet + SIZE_ETHERNET);
    size_ip = IP_HL(iph)*4;
    
    // packetの先頭アドレスから
    // ethernet_header_size+ip_header_size分インクリメントするとtcp_header
    tcph = (struct struct_tcp_header *)(packet + size_ip + SIZE_ETHERNET); 

    // sender macaddr
    print_packet_data(eh, iph, tcph);
}


