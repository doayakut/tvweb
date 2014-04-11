import os, re, glob
from Cheetah.Template import Template

def enum(**enums):
    return type('Enum', (), enums)
UType = enum(normal='n', blind='b')
PType = enum(simple='s', complex='c')

def main():
    i = u = ''

    i = raw_input('Select input file (.txt): ') # Input file number that will be used for 
    u = UType.blind if (raw_input('Select user type (Normal(n) or Blind(b)): ') == 'b') else UType.normal # User type refers to the vision (page will have accesibility features)

    if not u or not i:
        for file in glob.glob("*.txt"):
            generate(file[:-4], 'n')
            generate(file[:-4], 'b')
    

def generate(i, u):

    if not mkDirectories('result/' + u + '-' + i):
        return

    page_content = read_file(i, u)
    page_content['usertype'] = u
    
    # Generate Main File
    t = Template(file='templates/index.html', searchList=[page_content])
    fname = 'result/' + u + '-' + i + '/index.html'
    with open(fname, 'w') as f:
        # print 'Created ' + f.name
        f.write(str(t))

    # Generate Treeview File
    t = Template(file='templates/treeview.html', searchList=[page_content])
    fname = 'result/' + u + '-' + i + '/treeview.html'
    with open(fname, 'w') as f:
        # print 'Created ' + f.name
        f.write(str(t))

    # Generate Personalized File
    t = Template(file='templates/personalized.html', searchList=[page_content])
    fname = 'result/' + u + '-' + i + '/personalized.html'
    with open(fname, 'w') as f:
        # print 'Created ' + f.name
        f.write(str(t))

    # Generate Section Files
    for idx, section in enumerate(page_content['sections']):
        temp_page_content = {'title': '', 'sections': []}
        temp_page_content['title'] = page_content['title']

        temp_page_content['sections'].append(section)
        t = Template(file='templates/index.html', searchList=[temp_page_content])
        fname = 'result/' + u + '-' + i + '/' + str(idx) + '.html'
        with open(fname, 'w') as f:
            # print 'Created ' + f.name
            f.write(str(t))

    # Generate Link Section Files
    for section in page_content['sections']:
        for link_section in section['sections']:
            temp_page_content = {'title': '', 'sections': []}
            temp_page_content['title'] = page_content['title']
            temp_page_content['sections'].append(link_section)
            t = Template(file='templates/index.html', searchList=[temp_page_content])
            fname = 'result/' + u + '-' + i + '/' + 'leaves/' + link_section['link'] 
            with open(fname, 'w') as f:
                # print 'Created ' + f.name
                f.write(str(t))

<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> FETCH_HEAD
=======

>>>>>>> FETCH_HEAD
def mkDirectories(fp):
    if not os.path.exists(fp):
        os.makedirs(fp + '/leaves')
        print 'Directory ' + fp + ' created'
        return True
    else:
        print 'Directory ' + fp + ' already exists. Remove the directory'
        return False


<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> FETCH_HEAD
=======

>>>>>>> FETCH_HEAD
def read_file(i, u):

    fname = i + '.txt'
    fdict = {'pagetype': '', 'title': '', 'sections': []}
    curr_sc = {'title': '', 'link': '', 'pars': [], 'sections': []}

    state = -2 # -2,-1,0,1 // waiting for type, title, section_title, paragraph
    par_count = 0
    sec_count = 0

    with open(fname, 'r') as f:
        for line_idx, line in enumerate(f):
            if state == -2: # Reading Title
                fdict['pagetype'] = line[0:1].lower()
                state = -1
            elif state == -1: # Reading Title
                fdict['title'] = line
                state = 0
            elif state == 0: # Reading section header
                curr_sc['title'] = line
                curr_sc['link'] = str(sec_count) + '.html'
                state = 1
                sec_count += 1
            elif state == 1:
                if not line.strip():
                    fdict['sections'].append(curr_sc)
                    par_count = 0
                    curr_sc = {'title': '', 'link': '', 'pars': [], 'sections': []}
                    state = 0
                else:
<<<<<<< HEAD
<<<<<<< HEAD
                    if u != UType.blind or fdict['pagetype'] == 's' or par_count < 2:
=======
                    if u != UType.blind or par_count < 2:
>>>>>>> FETCH_HEAD
=======
                    if u != UType.blind or par_count < 2:
>>>>>>> FETCH_HEAD
                        par, link_scs = process_paragraph(line)
                        curr_sc['pars'].append(par)
                        for link_sc in link_scs:
                            curr_sc['sections'].append(link_sc)
                    par_count += 1
<<<<<<< HEAD
<<<<<<< HEAD
        fdict['sections'].append(curr_sc)
=======
>>>>>>> FETCH_HEAD
=======
>>>>>>> FETCH_HEAD
    return fdict

def process_paragraph(par):
    all_ss = par.split('.')
    stp_ss = strip_links(all_ss)
    link_scs = []
    curr_link_cs = {'title': '', 'link': '', 'pars': [], 'sections': []}

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
                    curr_link_cs['title'] = ' '.join(re.sub(r'\W+', '', w) for w in words[1:link_word_count + 1])
                    curr_link_cs['link'] = link_href + '.html'
                    curr_link_cs['pars'] = ['.'.join(stp_ss[ss_idx: ss_idx+4])]
                    link_scs.append(curr_link_cs)
                    curr_link_cs = {'title': '', 'link': '', 'pars': [], 'sections': []}

                    words[1] = '<a href="leaves/' + link_href + '.html">' + words[1]
<<<<<<< HEAD
<<<<<<< HEAD
                    # print words
=======
>>>>>>> FETCH_HEAD
=======
>>>>>>> FETCH_HEAD
                    words[link_word_count] = words[link_word_count] + '</a>'
                    segs[i] = ' '.join(words[1:])
            rep_ss.append(' '.join(segs))
        else:
            rep_ss.append(ss)

    return '.'.join(rep_ss),  link_scs


<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> FETCH_HEAD
=======

>>>>>>> FETCH_HEAD
def strip_links(ss):
    # all sentences with stripped of deliminators
    stp_ss = []
    for ss in ss:
        if '|' in ss:
            segs = ss.split('|')
            for i, s in enumerate(segs):
                if i != 0: # skip first item
                    segs[i] = ' '.join(segs[i].split(' ')[1:]) # strip from link count number
            stp_ss.append(' '.join(segs))
        else:
            stp_ss.append(ss)

    return stp_ss

def replace_links(par):
    all_ss = par.split('.')
    stp_ss = strip_links(par)

    

if __name__ == "__main__":
    main()