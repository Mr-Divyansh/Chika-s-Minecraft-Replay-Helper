import zipfile, shutil, os, json
base='D-Web-Studio-Chika-Replay-Helper-1.2.1+mc26.1.2.jar'
work='jar_work_200'
if os.path.exists(work): shutil.rmtree(work)
os.makedirs(work)
with zipfile.ZipFile(base,'r') as z:
    z.extractall(work)
for root,_,files in os.walk('out_compile'):
    for f in files:
        src=os.path.join(root,f)
        rel=os.path.relpath(src,'out_compile')
        dst=os.path.join(work,rel)
        os.makedirs(os.path.dirname(dst),exist_ok=True)
        shutil.copy2(src,dst)
mod=json.load(open(os.path.join(work,'fabric.mod.json'),encoding='utf-8'))
mod['version']='2.0.0+mc26.1.2'
json.dump(mod,open(os.path.join(work,'fabric.mod.json'),'w',encoding='utf-8'),indent=2)
out='D-Web-Studio-Chika-Replay-Helper-2.0.0+mc26.1.2.jar'
if os.path.exists(out): os.remove(out)
with zipfile.ZipFile(out,'w',zipfile.ZIP_DEFLATED) as z:
    for root,_,files in os.walk(work):
        for f in files:
            p=os.path.join(root,f)
            arc=os.path.relpath(p,work)
            z.write(p,arc)
print('created',out)
