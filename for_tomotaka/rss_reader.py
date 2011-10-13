#!/usr/local/bin/python 
# -*- encoding:utf-8 -*-

def parse(rss):
    if not isinstance(rss, et.Element):
        print 'Error: arg is not ElementTree.Element (rss_reader.parse)'
        return None
    
    result = {}
    for r in rss.getiterator('channel'):
        for e in list(r):
            if (e.tag != 'image') and (e.tag != 'item'):
                result[e.tag] = e.text

    items = []
    for r in rss.getiterator('item'):
        item = {}
        for e in list(r):
            item[e.tag] = e.text
        items.append(item)
    result['items'] = items
    return result

import sys 
import urllib
import xml.etree.ElementTree as et

if __name__ == "__main__":
    argv = sys.argv
    argc = len(argv)
    if argc <= 1:
        url = 'http://headlines.yahoo.co.jp/rss/wsj_bus.xml' # ウォール・ストリート・ジャーナル
    else:
        url = argv[1] # コマンドライン入力からurl

    try:
        responce = urllib.urlopen(url)
        rss = et.fromstring(responce.read())
    except IOError:
        print 'IOError: can not get rss feed through web'
        quit()
    
    result = parse(rss)
    for tag, value in result.items():
        if isinstance(value, list):
            for e in value:
                for eTag, eValue in e.items():
                    print '%s: %s' % (eTag, eValue)
                print ''
        else:
            print '%s: %s' % (tag, value)
