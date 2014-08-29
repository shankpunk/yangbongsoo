
// FinalProjectColorView.cpp : CFinalProjectColorView Ŭ������ ����
//

#include "stdafx.h"
// SHARED_HANDLERS�� �̸� ����, ����� �׸� �� �˻� ���� ó���⸦ �����ϴ� ATL ������Ʈ���� ������ �� ������
// �ش� ������Ʈ�� ���� �ڵ带 �����ϵ��� �� �ݴϴ�.
#ifndef SHARED_HANDLERS
#include "FinalProjectColor.h"
#endif

#include "FinalProjectColorDoc.h"
#include "FinalProjectColorView.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CFinalProjectColorView

IMPLEMENT_DYNCREATE(CFinalProjectColorView, CView)

BEGIN_MESSAGE_MAP(CFinalProjectColorView, CView)
	// ǥ�� �μ� ����Դϴ�.
	ON_COMMAND(ID_FILE_PRINT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, &CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, &CFinalProjectColorView::OnFilePrintPreview)
	ON_WM_CONTEXTMENU()
	ON_WM_RBUTTONUP()
	ON_COMMAND(IDM_ZOOM_IN, &CFinalProjectColorView::OnZoomIn)
	ON_COMMAND(IDM_NEAREST, &CFinalProjectColorView::OnNearest)
	ON_COMMAND(IDM_BILINEAR, &CFinalProjectColorView::OnBilinear)
	ON_COMMAND(IDM_ZOOM_OUT, &CFinalProjectColorView::OnZoomOut)
	ON_COMMAND(IDM_MEDIAN_SUB, &CFinalProjectColorView::OnMedianSub)
	ON_COMMAND(IDM_MEAN_SUB, &CFinalProjectColorView::OnMeanSub)
	ON_COMMAND(IDM_TRANSLATION, &CFinalProjectColorView::OnTranslation)
	ON_COMMAND(IDM_MIRROR_HOR, &CFinalProjectColorView::OnMirrorHor)
	ON_COMMAND(IDM_MIRROR_VER, &CFinalProjectColorView::OnMirrorVer)
	ON_COMMAND(IDM_ROTATION, &CFinalProjectColorView::OnRotation)
END_MESSAGE_MAP()

// CFinalProjectColorView ����/�Ҹ�

CFinalProjectColorView::CFinalProjectColorView()
{
	// TODO: ���⿡ ���� �ڵ带 �߰��մϴ�.

}

CFinalProjectColorView::~CFinalProjectColorView()
{
}

BOOL CFinalProjectColorView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: CREATESTRUCT cs�� �����Ͽ� ���⿡��
	//  Window Ŭ���� �Ǵ� ��Ÿ���� �����մϴ�.

	return CView::PreCreateWindow(cs);
}

// CFinalProjectColorView �׸���

void CFinalProjectColorView::OnDraw(CDC* pDC)
{
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	if (!pDoc)
		return;

	// TODO: ���⿡ ���� �����Ϳ� ���� �׸��� �ڵ带 �߰��մϴ�.
	int i,j;
	unsigned char R,G,B;
	for (i=0; i<pDoc->m_height; i++) {
		for (j=0; j<pDoc->m_width; j++) {
			R=pDoc->m_InputImageR[i][j];
			G=pDoc->m_InputImageG[i][j];
			B=pDoc->m_InputImageB[i][j];
			pDC->SetPixel(j+5, i+5, RGB(R,G,B));
		}
	}

	for (i=0; i<pDoc->m_Re_height; i++) {
		for (j=0; j<pDoc->m_Re_width; j++) {
			R=pDoc->m_OutputImageR[i][j];
			G=pDoc->m_OutputImageG[i][j];
			B=pDoc->m_OutputImageB[i][j];
			pDC->SetPixel(j+pDoc->m_width+10, i+5, RGB(R,G,B));
		}
	}
}


// CFinalProjectColorView �μ�


void CFinalProjectColorView::OnFilePrintPreview()
{
#ifndef SHARED_HANDLERS
	AFXPrintPreview(this);
#endif
}

BOOL CFinalProjectColorView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// �⺻���� �غ�
	return DoPreparePrinting(pInfo);
}

void CFinalProjectColorView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: �μ��ϱ� ���� �߰� �ʱ�ȭ �۾��� �߰��մϴ�.
}

void CFinalProjectColorView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: �μ� �� ���� �۾��� �߰��մϴ�.
}

void CFinalProjectColorView::OnRButtonUp(UINT /* nFlags */, CPoint point)
{
	ClientToScreen(&point);
	OnContextMenu(this, point);
}

void CFinalProjectColorView::OnContextMenu(CWnd* /* pWnd */, CPoint point)
{
#ifndef SHARED_HANDLERS
	theApp.GetContextMenuManager()->ShowPopupMenu(IDR_POPUP_EDIT, point.x, point.y, this, TRUE);
#endif
}


// CFinalProjectColorView ����

#ifdef _DEBUG
void CFinalProjectColorView::AssertValid() const
{
	CView::AssertValid();
}

void CFinalProjectColorView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CFinalProjectColorDoc* CFinalProjectColorView::GetDocument() const // ����׵��� ���� ������ �ζ������� �����˴ϴ�.
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CFinalProjectColorDoc)));
	return (CFinalProjectColorDoc*)m_pDocument;
}
#endif //_DEBUG


// CFinalProjectColorView �޽��� ó����


void CFinalProjectColorView::OnZoomIn()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomIn();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnNearest()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnNearest();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnBilinear()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnBilinear();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnZoomOut()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnZoomOut();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMedianSub()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMedianSub();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMeanSub()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMeanSub();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnTranslation()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnTranslation();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMirrorHor()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMirrorHor();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnMirrorVer()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnMirrorVer();
	Invalidate(TRUE);
}


void CFinalProjectColorView::OnRotation()
{
	// TODO: ���⿡ ��� ó���� �ڵ带 �߰��մϴ�.
	CFinalProjectColorDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);

	pDoc->OnRotation();
	Invalidate(TRUE);
}
