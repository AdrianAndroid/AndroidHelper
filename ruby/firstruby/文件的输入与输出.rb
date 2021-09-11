#!/usr/bin/ruby
 
val1 = "This is variable one"
val2 = "This is variable two"
puts val1
puts val2

puts "gets语句"
puts "Enter a value :"
# val = gets
# puts val

puts "putc语句"
# str="Hello Ruby!"
# putc str

print "Hello World"
print "Good Morning"

puts "模式	描述"
puts "r	只读模式。文件指针被放置在文件的开头。这是默认模式。"
puts "r+	读写模式。文件指针被放置在文件的开头。"
puts "w	只写模式。如果文件存在，则重写文件。如果文件不存在，则创建一个新文件用于写入。"
puts "w+	读写模式。如果文件存在，则重写已存在的文件。如果文件不存在，则创建一个新文件用于读写。"
puts "a	只写模式。如果文件存在，则文件指针被放置在文件的末尾。也就是说，文件是追加模式。如果文件不存在，则创建一个新文件用于写入。"
puts "a+	读写模式。如果文件存在，则文件指针被放置在文件的末尾。也就是说，文件是追加模式。如果文件不存在，则创建一个新文件用于读写。"

File.open("file.rb", "r") do |aFile|
    if aFile
        content = aFile.sysread(20000)
        puts content
    else
        puts "Unable to open file!"
    end
end

aFile = File.new("file.rb", "r")
if aFile
   content = aFile.sysread(200)
   puts content
else
   puts "Unable to open file!"
end

aFile = File.new("file.rb", "r+")
if aFile
    aFile.syswrite("ABCDEFs a s\nimple text fil\ne for testin\ng purpose.")
else 
    puts "Uable to open file!"
end

puts "each_byte方法"
aFile = File.new("file.rb", "r+")
if aFile
    aFile.syswrite("ABCDEF")
    aFile.rewind
    aFile.each_byte { |ch| putc ch; putc ?. }
else 
    puts "Unable to open file!"
end

puts "\nIO.readlines"
arr = IO.readlines("file.rb")
puts arr[0]
puts arr[1]

puts "\nIO.foreach"
# IO.foreach("file.rb"){|block| puts block}

puts "重命名和删除文件"
# File.name("test1.txt", "text2.txt")
# File.delete("text2.txt")

puts "文件模式与所有权"
# file = File.new( "test.txt", "w" )
# file.chmod( 0755 )

puts "掩码	描述"
puts "0700	rwx 掩码，针对所有者"
puts "0400	r ，针对所有者"
puts "0200	w ，针对所有者"
puts "0100	x ，针对所有者"
puts "0070	rwx 掩码，针对所属组"
puts "0040	r ，针对所属组"
puts "0020	w ，针对所属组"
puts "0010	x ，针对所属组"
puts "0007	rwx 掩码，针对其他人"
puts "0004	r ，针对其他人"
puts "0002	w ，针对其他人"
puts "0001	x ，针对其他人"
puts "4000	执行时设置用户 ID"
puts "2000	执行时设置所属组 ID"
puts "1000	保存交换文本，甚至在使用后也会保存"



puts "文件查询"
# File.open("file.rb") if File::exists?{"file.rb"}
# File.file? {"text.txt"}
puts "一个目录"
# File::directory?("/usr/local/bin") # => true
# File::directory("file.rb")
# File.readable?("test.txt")
# File.writeble?("test.txt")
# File.executable?("test.txt")
# File.zero?("test.txt")
# File.size?("text.txt")
# File::ftype("test.txt")
# File::ctiime("test.txt")
# File::mtime("text.txt")
# File::atime("text.txt")

puts "Ruby中的目录"
# Dir.chdir("/usr/bin")
# puts Dir.pwd
# puts Dir.entries("/usr/bin").join(' ')
# Dir.foreach("/usr/bin") do |entry|
    # puts entry
# end

puts "创建目录"
# Dir.mkdir("myNewidr")
# Dir.mkdir("mynewdir", 755)

puts "删除目录"
# Dir.delete("testdir")

puts "创建文件&临时目录"
# require 'tmpdir'
# tempfilename = File.join(Dir.tmpdir, "tingtong")
# tempfile = File.new(tempfilename, "w")
# tempfile.puts "This is a temporary file"
# tempfile.close
# File.delete(tempfilename)

require 'tempfile'
# f = empfile.new('tingtong')
# f.puts "Hello"
# puts f.path
# f.close

puts "Ruby File 类和方法"
puts " 序号	方法 & 描述"
puts " 1	File::atime( path)"
puts " 返回 path 的最后访问时间。"
puts " 2	File::basename( path[, suffix])"
puts " 返回 path 末尾的文件名。如果指定了 suffix，则它会从文件名末尾被删除。"
puts " 例如：File.basename(\"/home/users/bin/ruby.exe\") #=> \"ruby.exe\""
puts " 3	File::blockdev?( path)"
puts " 如果 path 是一个块设备，则返回 true。"
puts " 4	File::chardev?( path)"
puts " 如果 path 是一个字符设备，则返回 true。"
puts " 5	File::chmod( mode, path...)"
puts " 改变指定文件的权限模式。"
puts " 6	File::chown( owner, group, path...)"
puts " 改变指定文件的所有者和所属组。"
puts " 7	File::ctime( path)"
puts " 返回 path 的最后一个 inode 更改时间。"
puts " 8	File::delete( path...)"
puts " File::unlink( path...)"
puts " 删除指定的文件。"
puts " 9	File::directory?( path)"
puts " 如果 path 是一个目录，则返回 true。"
puts " 10	File::dirname( path)"
puts " 返回 path 的目录部分，不包括最后的文件名。"
puts " 11	File::executable?( path)"
puts " 如果 path 是可执行的，则返回 true。"
puts " 12	File::executable_real?( path)"
puts " 如果 path 通过真正的用户权限是可执行的，则返回 true。"
puts " 13	File::exist?( path)"
puts " 如果 path 存在，则返回 true。"
puts " 1	File::expand_path( path[, dir])"
puts " 返回 path 的绝对路径，扩展 ~ 为进程所有者的主目录，~user 为用户的主目录。相对路径是相对于 dir 指定的目录，如果 dir 被省略则相对于当前工作目录。"
puts " 14	File::file?( path)"
puts " 如果 path 是一个普通文件，则返回 true。"
puts " 15	File::ftype( path)"
puts " 返回下列其中一个字符串，表示文件类型："
puts " file - 普通文件"
puts " directory - 目录"
puts " characterSpecial - 字符特殊文件"
puts " blockSpecial - 块特殊文件"
puts " fifo - 命名管道（FIFO）"
puts " link - 符号链接"
puts " socket - Socket"
puts " unknown - 未知的文件类型"
puts " 16	File::grpowned?( path)"
puts " 如果 path 由用户的所属组所有，则返回 true。"
puts " 17	File::join( item...)"
puts " 返回一个字符串，由指定的项连接在一起，并使用 File::Separator 进行分隔。"
puts " 例如：File::join(\" \", \"home\", \"usrs\", \"bin\") # => \"/home/usrs/bin\""
puts " 18	File::link( old, new)"
puts " 创建一个到文件 old 的硬链接。"
puts " 19	File::lstat( path)"
puts " 与 stat 相同，但是它返回自身符号链接上的信息，而不是所指向的文件。"
puts " 20	File::mtime( path)"
puts " 返回 path 的最后一次修改时间。"
puts " 21	File::new( path[, mode=\"r\"])"
puts " File::open( path[, mode=\"r\"])"
puts " File::open( path[, mode=\"r\"]) {|f| ...}"
puts " 打开文件。如果指定了块，则通过传递新文件作为参数来执行块。当块退出时，文件会自动关闭。这些方法有别于 Kernel.open，即使 path 是以 | 开头，后续的字符串也不会作为命令运行。"
puts " 22	File::owned?( path)"
puts " 如果 path 由有效的用户所有，则返回 true。"
puts " 23	File::pipe?( path)"
puts " 如果 path 是一个管道，则返回 true。"
puts " 24	File::readable?( path)"
puts " 如果 path 是可读的，则返回 true。"
puts " 25	File::readable_real?( path)"
puts " 如果 path 通过真正的用户权限是可读的，则返回 true。"
puts " 25	File::readlink( path)"
puts " 返回 path 所指向的文件。"
puts " 26	File::rename( old, new)"
puts " 改变文件名 old 为 new。"
puts " 27	File::setgid?( path)"
puts " 如果设置了 path 的 set-group-id 权限位，则返回 true。"
puts " 28	File::setuid?( path)"
puts " 如果设置了 path 的 set-user-id 权限位，则返回 true。"
puts " 29	File::size( path)"
puts " 返回 path 的文件大小。"
puts " 30	File::size?( path)"
puts " 返回 path 的文件大小，如果为 0 则返回 nil。"
puts " 31	File::socket?( path)"
puts " 如果 path 是一个 socket，则返回 true。"
puts " 32	File::split( path)"
puts " 返回一个数组，包含 path 的内容，path 被分成 File::dirname(path) 和 File::basename(path)。"
puts " 33	File::stat( path)"
puts " 返回 path 上带有信息的 File::Stat 对象。"
puts " 34	File::sticky?( path)"
puts " 如果设置了 path 的 sticky 位，则返回 true。"
puts " 35	File::symlink( old, new)"
puts " 创建一个指向文件 old 的符号链接。"
puts " 36	File::symlink?( path)"
puts " 如果 path 是一个符号链接，则返回 true。"
puts " 37	File::truncate( path, len)"
puts " 截断指定的文件为 len 字节。"
puts " 38	File::unlink( path...)"
puts " 删除 path 给定的文件。"
puts " 39	File::umask([ mask])"
puts " 如果未指定参数，则为该进程返回当前的 umask。如果指定了一个参数，则设置了 umask，并返回旧的 umask。"
puts " 40	File::utime( atime, mtime, path...)"
puts " 改变指定文件的访问和修改时间。"
puts " 41	File::writable?( path)"
puts " 如果 path 是可写的，则返回 true。"
puts " 42	File::writable_real?( path)"
puts " 如果 path 通过真正的用户权限是可写的，则返回 true。"
puts " 43	File::zero?( path)"
puts " 如果 path 的文件大小是 0，则返回 true。"
puts " "
puts " "



puts "序号	方法 & 描述"
puts "1	f.atime"
puts "返回 f 的最后访问时间。"
puts "2	f.chmode( mode)"
puts "改变 f 的权限模式。"
puts "3	f.chown( owner, group)"
puts "改变 f 的所有者和所属组。"
puts "4	f.ctime"
puts "返回 f 的最后一个 inode 更改时间。"
puts "5	f.flock( op)"
puts "调用 flock(2)。op 可以是 0 或一个逻辑值或 File 类常量 LOCK_EX、LOCK_NB、LOCK_SH 和 LOCK_UN。"
puts "6	f.lstat"
puts "与 stat 相同，但是它返回自身符号链接上的信息，而不是所指向的文件。"
puts "7	f.mtime"
puts "返回 f 的最后修改时间。"
puts "8	f.path"
puts "返回用于创建 f 的路径名。"
puts "9	f.reopen( path[, mode=\"r\"])"
puts "重新打开文件。"
puts "10	f.truncate( len)"
puts "截断 f 为 len 字节。"


puts "Ruby Dir 类和方法"
类方法
puts "序号	方法 & 描述"
puts "1	Dir[pat]"
puts "Dir::glob( pat)"
puts "返回一个数组，包含与指定的通配符模式 pat 匹配的文件名："
puts "* - 匹配包含 null 字符串的任意字符串"
puts "** - 递归地匹配任意字符串"
puts "? - 匹配任意单个字符"
puts "[...] - 匹配封闭字符中的任意一个"
puts "{a,b...} - 匹配字符串中的任意一个"
puts "Dir[\"foo.*\"] # 匹配 \"foo.c\"、 \"foo.rb\" 等等"
puts "Dir[\"foo.?\"] # 匹配 \"foo.c\"、 \"foo.h\" 等等"
puts "2	Dir::chdir( path)"
puts "改变当前目录。"
puts "3	Dir::chroot( path)"
puts "改变根目录（只允许超级用户）。并不是在所有的平台上都可用。"
puts "4	Dir::delete( path)"
puts "删除 path 指定的目录。目录必须是空的。"
puts "5	Dir::entries( path)"
puts "返回一个数组，包含目录 path 中的文件名。"
puts "6	Dir::foreach( path) {| f| ...}"
puts "为 path 指定的目录中的每个文件执行一次块。"
puts "7	Dir::getwd"
puts "Dir::pwd"
puts "返回当前目录。"
puts "8	Dir::mkdir( path[, mode=0777])"
puts "创建 path 指定的目录。权限模式可被 File::umask 的值修改，在 Win32 的平台上会被忽略。"
puts "9	Dir::new( path)"
puts "Dir::open( path)"
puts "Dir::open( path) {| dir| ...}"
puts "返回 path 的新目录对象。如果 open 给出一个块，则新目录对象会传到该块，块会在终止前关闭目录对象。"
puts "10	Dir::pwd"
puts "参见 Dir::getwd。"
puts "11	Dir::rmdir( path)"
puts "Dir::unlink( path)"
puts "Dir::delete( path)"
puts "删除 path 指定的目录。目录必须是空的。"



实例方法
假设 d 是 Dir 类的一个实例：

puts "序号	方法 & 描述"
puts "1	d.close"
puts "关闭目录流。"
puts "2	d.each {| f| ...}"
puts "为 d 中的每一个条目执行一次块。"
puts "3	d.pos"
puts "d.tell"
puts "返回 d 中的当前位置。"
puts "4	d.pos= offset"
puts "设置目录流中的位置。"
puts "5	d.pos= pos"
puts "d.seek(pos)"
puts "移动到 d 中的某个位置。pos 必须是一个由 d.pos 返回的值或 0。"
puts "6	d.read"
puts "返回 d 的下一个条目。"
puts "7	d.rewind"
puts "移动 d 中的位置到第一个条目。"
puts "8	d.seek(po s)"
puts "参见 d.pos=pos。"
puts "9	d.tell"
puts "参见 d.pos。"