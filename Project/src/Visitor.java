public interface Visitor {
    void visit(BookDepartment bookDepartment);
    void visit(MusicDepartment musicDepartment);
    void visit(SoftwareDepartment softwareDepartment);
    void visit(VideoDepartment videoDepartment);
}
