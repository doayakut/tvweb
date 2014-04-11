
import os
import re

filename = ''
filepath = ''
title = ''
regex = re.compile(r"^|.*|$", re.IGNORECASE)

state = -1 # -1,0,1
section_count = 0
par_count = 0
link_count = 0


m_fname = ''
t_fname = ''
c_fname = ''
c_title = ''
l_fname = ''
l_title = ''



def main():
    global filename, filepath, title, state, section_count, par_count, t_fname, m_fname, c_fname, c_title, l_fname, l_title

    filepath = raw_input('Select file: ') 
    filename = filepath + '.txt'    
    filepath = 'result/' + filepath

    if not mkDirectories():
        return

    m_fname = os.path.join(filepath, 'index.html')
    t_fname = os.path.join(filepath, 'treeview.html')
    p_fname = os.path.join(filepath, 'personalized.html')
    c_fname = ''
    c_title = ''
    l_fname = ''
    l_title = ''

    with open(filename, 'r') as f:
        print 'Opened ' + filename
        for line_idx, line in enumerate(f):
            if state == -1: # Reading Title
                title = line
                
                mkHtml(m_fname)
                mkHtml(t_fname)
                writeHeader(m_fname, title)
                writeTVHeader(t_fname, title)
                
                state = 0

            elif state == 0: # Reading section header
                c_title = line
                c_fname = os.path.join(filepath, str(section_count) + '.html')
                
                mkHtml(c_fname)
                writeHeader(c_fname, c_title)
                writeSubsec(m_fname, c_title, True, str(section_count))
                writeOpenTVNode(t_fname, c_title, '')

                section_count += 1
                state = 1

            elif state == 1:
                if not line.strip():
                    state = 0
                    writeCloseTVNode(t_fname)


                else:
                    par_count += 1

                    writePar(c_fname, line)
                    writePar(m_fname, line)

    writeTVFooter(t_fname)
                    
    print '# of sections: ' + str(section_count)
    print '# of paragraphs: ' + str(par_count)


def mkDirectories():
    if not os.path.exists(filepath):
        os.makedirs(filepath + '/leaves')
        print 'Directory ' + filepath + ' created'
        return True
    else:
        print 'Directory ' + filepath + ' already exists. Remove the directory'
        return False

def mkHtml(fname):
    if not os.path.isfile(fname):
        with open(fname, 'w') as html:
            print 'Created ' + html.name
            return True
    else:
        return False
                                    
def writeHeader(fname, title):
    with open(fname, 'a') as html:
        text = '<!DOCTYPE html>' + '\n' + '<html>' + '\n' + '<head>' + '\n' + '<title>' + title + '</title>' + '\n' + '<link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,latin-ext,vietnamese,greek,greek-ext,cyrillic-ext,cyrillic" rel="stylesheet" type="text/css"><style> body { width:640px; margin:auto; font-family: "Helvetica Neue",Helvetica,Arial,sans-serif; font-size:1em; line-height:1.4em } a {color: #428bca; text-decoration: none} a:hover {text-decoration: underline;} p {font-family: Georgia, Times, serif;} .main{ background-color: #f3f3f3; padding: 1em} </style>' + '\n' + '</head>' + '\n' + '<body>' + '\n' +'<h1>' + title + '</h1>' + '\n' + '<div class="main">'
        html.write(text)
    return

def writeTVHeader(fname, title):
    with open(fname, 'a') as html:
<<<<<<< HEAD
<<<<<<< HEAD
        text = '<!DOCTYPE html>' + '\n' + '<html>' + '\n' + '<head>' + '\n' + '<title>' + title + '</title>' + '\n' + '<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">' + '\n' + '<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>' + '\n' + '<link rel="stylesheet" type="text/css" href="/tvweb2/css/exp.css">' + '\n' + '<script type="text/javascript" src="/tvweb2/js/exp.js"></script>' + '\n' + '<style> body { width:640px; margin:auto; font-family: "Helvetica Neue",Helvetica,Arial,sans-serif; line-height:1.4em } ul li {line-height:2em;}  a {color: #428bca; text-decoration: none} a:hover {text-decoration: underline;} p {font-family: Georgia, Times, serif;} .main{ background-color: #f3f3f3; padding: 1em} .btn{display:inline-block;margin-bottom:0;font-weight:400;text-align:center;vertical-align:middle;cursor:pointer;background-image:none;border:1px solid transparent;white-space:nowrap;padding:2px 4px;font-size:14px;line-height:1.42857143;border-radius:0px;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none} .btn-default{color:#333;background-color:#fff;border-color:#ccc;}</style>' + '\n' + '</head>' + '\n' + '<body>' + '\n' + '<h1>' + title + '</h1>' + '\n' + '<ul class="fa-ul tree">'
=======
        text = '<!DOCTYPE html>' + '\n' + '<html>' + '\n' + '<head>' + '\n' + '<title>' + title + '</title>' + '\n' + '<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">' + '\n' + '<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>' + '\n' + '<link rel="stylesheet" type="text/css" href="/tvweb/css/exp.css">' + '\n' + '<script type="text/javascript" src="/tvweb/js/exp.js"></script>' + '\n' + '<style> body { width:640px; margin:auto; font-family: "Helvetica Neue",Helvetica,Arial,sans-serif; line-height:1.4em } ul li {line-height:2em;}  a {color: #428bca; text-decoration: none} a:hover {text-decoration: underline;} p {font-family: Georgia, Times, serif;} .main{ background-color: #f3f3f3; padding: 1em} .btn{display:inline-block;margin-bottom:0;font-weight:400;text-align:center;vertical-align:middle;cursor:pointer;background-image:none;border:1px solid transparent;white-space:nowrap;padding:2px 4px;font-size:14px;line-height:1.42857143;border-radius:0px;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none} .btn-default{color:#333;background-color:#fff;border-color:#ccc;}</style>' + '\n' + '</head>' + '\n' + '<body>' + '\n' + '<h1>' + title + '</h1>' + '\n' + '<ul class="fa-ul tree">'
>>>>>>> FETCH_HEAD
=======
        text = '<!DOCTYPE html>' + '\n' + '<html>' + '\n' + '<head>' + '\n' + '<title>' + title + '</title>' + '\n' + '<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">' + '\n' + '<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>' + '\n' + '<link rel="stylesheet" type="text/css" href="/tvweb/css/exp.css">' + '\n' + '<script type="text/javascript" src="/tvweb/js/exp.js"></script>' + '\n' + '<style> body { width:640px; margin:auto; font-family: "Helvetica Neue",Helvetica,Arial,sans-serif; line-height:1.4em } ul li {line-height:2em;}  a {color: #428bca; text-decoration: none} a:hover {text-decoration: underline;} p {font-family: Georgia, Times, serif;} .main{ background-color: #f3f3f3; padding: 1em} .btn{display:inline-block;margin-bottom:0;font-weight:400;text-align:center;vertical-align:middle;cursor:pointer;background-image:none;border:1px solid transparent;white-space:nowrap;padding:2px 4px;font-size:14px;line-height:1.42857143;border-radius:0px;-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;user-select:none} .btn-default{color:#333;background-color:#fff;border-color:#ccc;}</style>' + '\n' + '</head>' + '\n' + '<body>' + '\n' + '<h1>' + title + '</h1>' + '\n' + '<ul class="fa-ul tree">'
>>>>>>> FETCH_HEAD
        html.write(text)
    return
        
def writeSubsec(fname, title, link, href):
    with open(fname, 'a') as html:
        titlehtml = title
        if link:
            titlehtml = '<a href="' + href + '.html">' + title + '</a>'
        text = '<h3>' + titlehtml + '</h3>'
        html.write(text)
    return

def writeOpenTVNode(fname, text, href):
    with open(fname, 'a') as html:
        if not href:
            link = text
        else:
            link = '<a href="' + href + '.html">' + text + ' </a>'

        html_str = '<li>' + link + '<button class="colexp btn btn-default pull-right" > <i class="fa fa-minus"></i> </button> <ul class="fa-ul">'
        html.write(html_str)

def writeCloseTVNode(fname):
    with open(fname, 'a') as html:
        text = '</ul></li>'
        html.write(text)

def writeTVFooter(fname):
    with open(fname, 'a') as html:
        text = '<script> \n $("button.colexp").click(function(){ \n $(this).children(":first").toggleClass("fa-plus").toggleClass("fa-minus"); \n $(this).next().toggle(); \n }); \n $("li").each(function(){ \n if($(this).children("ul").has("li").length == 0){ \n $(this).children("ul").remove() \n $(this).children("button").remove() \n } \n }); \n </script>'
        html.write(text)

def writeSimplePar(fname, text):
    with open(fname, 'a') as html:
        html.write('<p>' + text+ '</p>') 

def writePar(fname, text):

    # all sentences in paragraph
    all_ss = text.split('.')
    
    # all sentences with stripped of deliminators
    stp_ss = []
    for ss in all_ss:
        if '|' in ss:
            segs = ss.split('|')
            for i, s in enumerate(segs):
                if i != 0: # skip first item
                    segs[i] = ' '.join(segs[i].split(' ')[1:]) # strip from link count number
            stp_ss.append(' '.join(segs))
        else:
            stp_ss.append(ss)

    # all sentences with stripped of replaced
    rep_ss = []
    for ss_idx, ss in enumerate(all_ss):
        if '|' in ss:
            segs = ss.split('|') # ['', '3 ali all eee raogejrgo', '2 re aerga aerager']
            for i, s in enumerate(segs):
                if i != 0:
                    words = segs[i].split(' ')
                    link_word_count = int(words[0])
                    link_href = re.sub(r'\W+', '', ''.join(words[1:link_word_count + 1]).lower())
                    makeLeaf(words[1:link_word_count + 1],link_href, stp_ss, ss_idx)
                    words[1] = '<a href="leaves/' + link_href + '.html">' + words[1]
                    words[link_word_count] = words[link_word_count] + '</a>'
                    segs[i] = ' '.join(words[1:])
            rep_ss.append(' '.join(segs))
        else:
            rep_ss.append(ss)

    with open(fname, 'a') as html:
        html.write('<p>' + '.'.join(rep_ss) + '</p>') 

def makeLeaf(words, link_href, stp_ss, ss_idx):
    global link_count
    link_count += 1

    leafname = filepath + '/leaves/' + link_href + '.html'
    if mkHtml(leafname):
        writeHeader(leafname , ' '.join(words))
        writeSimplePar(leafname , '.'.join(stp_ss[ss_idx: ss_idx+4]))
        writeOpenTVNode(t_fname, ' '.join(words), 'leaves/' + link_href)
        writeCloseTVNode(t_fname)
    return 


if __name__ == "__main__":
    main()